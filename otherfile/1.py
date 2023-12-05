# coding=gbk
import time
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from py2neo import *
from urllib.parse import urljoin
import pymysql

# ������
Legal_list = ['�����ڿ�', '�ڿ�', '˶ʿ', '��ʿ']
Driver_Path = "D:/����/chromedriver103.exe" # ������ַ������ʵ������޸�
query = "AF=���ִ�ѧ���ѧԺ OR AF=���ִ�ѧ�������ѧ�뼼��ѧԺ"
DBHOST = "localhost"
DBUSER = "root"
DBPASS = "myf021105"
DBNAME = "p2a"
stimulate_times = 100
Pap2Au = []
Au2Institution = []
Pap2Institution = []
count = 0
papers_need = 400


# ������

# ���ؼ��ʷֽ�Ϊ����ؼ���
def splitKeywords(keywords):
    domain = []
    if keywords == '':
        return domain
    keywords = keywords.replace(' ', '')
    domain = keywords.split(';')
    return domain


# �������ֽ�Ϊ�������
def institute2list(institute):
    institute = institute.replace(' ', '')
    institutes = []
    if not ('0' <= institute[0] <= '9'):
        institutes.append(institute)
        return institutes
    i, j = 0, 0
    while (i < len(institute)):
        while (institute[i] != '.'):
            i += 1
        j = i + 1
        while j < len(institute) and (not ('0' <= institute[j] <= '9')):
            j += 1
        institutes.append(institute[i + 1:j])
        i = j
    for item in range(len(institutes)):
        if ("֪ʶ����" in institutes[item]) or ("���ż���" in institutes[item]):
            institutes[item] = "���ִ�ѧ���ż�����֪ʶ���̽������ص�ʵ����"
    return institutes


# ����Neo4j
# ���ӳɹ�����ͼ�׾�� ����ʧ�ܷ��ؿն���
def connectNeo4j():
    try:
        graph = Graph('bolt://localhost:7687', auth=('neo4j', 'myf021105'))
        graph.delete_all()
        return graph
    except:
        return None


# ��������
# ���óɹ�������������� ����ʧ�ܷ��ؿն���
def createDriver():
    try:
        # �����������Ļ���
        options = webdriver.ChromeOptions()
        # ����chrome������ͼƬ������ٶ�
        options.add_experimental_option("prefs", {"profile.managed_default_content_settings.images": 2})
        # ���ò���ʾ����
        options.add_argument('--headless')
        driver = webdriver.Chrome(executable_path="D:/����/chromedriver103.exe", options=options)
        driver.get("https://kns.cnki.net/kns8/AdvSearch")
        return driver
    except:
        return None


# ��ȡ������Ϣ
def getBasicInf(driver, term):

    title_xpath = '''//*[@id="gridTable"]/table/tbody/tr[''' + str(term) + ''']/td[2]'''
    author_xpath = '''//*[@id="gridTable"]/table/tbody/tr[''' + str(term) + ''']/td[3]'''
    source_xpath = '''//*[@id="gridTable"]/table/tbody/tr[''' + str(term) + ''']/td[4]'''
    date_xpath = '''//*[@id="gridTable"]/table/tbody/tr[''' + str(term) + ''']/td[5]'''
    database_xpath = '''//*[@id="gridTable"]/table/tbody/tr[''' + str(term) + ''']/td[6]'''
    title = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, title_xpath))).text
    authors = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, author_xpath))).text
    source = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, source_xpath))).text
    date = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, date_xpath))).text
    database = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, database_xpath))).text
    return title, authors, source, date, database


# ��ȡ���߻���
def getInstitution(driver):
    institute = WebDriverWait(driver, 30).until(EC.presence_of_element_located(
        (By.XPATH, "/html[1]/body[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[3]/div[1]/h3[2]"))).text
    instu = institute2list(institute)
    return instu


# ��ȡժҪ
def getAbstract(driver):
    abstract = WebDriverWait(driver, 30).until(
        EC.presence_of_element_located((By.CLASS_NAME, "abstract-text"))
    ).text
    return abstract


# ��ȡ�����б�
# ���������� ����ֵ�������б�
def getFunds(driver):
    fund = []
    try:
        funds = WebDriverWait(driver, 5).until(
            EC.presence_of_element_located((By.CLASS_NAME, "funds"))
        ).text[:-1]
        funds = funds.replace(' ', '')
        fund = funds.split('��')
    except:
        fund = []
    finally:
        return fund


# ��ȡ�ؼ���
def getDomain(driver):
    try:
        keywords = WebDriverWait(driver, 5).until(
            EC.presence_of_element_located((By.CLASS_NAME, "keywords"))).text[:-1]
    except:
        keywords = ''
    domain = splitKeywords(keywords)
    return domain


# ƥ�����������
def matchAuthor2Institution(driver, au, instu, au2institution):
    for i in range(len(au)):
        sup = []
        try:
            sup = WebDriverWait(driver, 5).until(EC.presence_of_element_located(
                (By.XPATH, '''//*[@id="authorpart"]/span[''' + str(i + 1) + ''']/a/sup'''))).text
            sup = sup.split(',')
        except:
            sup = [1]
        finally:
            for item in sup:
                value = (au[i], instu[int(item) - 1])
                au2institution.append(value)
                Au2Institution.append(value)


# ƥ�����������
def matchPap2Institution(title, instu, pap2institution):
    for instut in instu:
        value = (title, instut)
        pap2institution.append(value)
        Pap2Institution.append(value)


# ƥ������������
def matchPaper2Author(au, title, pap2au):
    for author in au:
        value = (title, author)
        pap2au.append(value)
        Pap2Au.append(value)


# ��ȡ���ģ�ֻ��һ������
def getCite_1(driver, cite):
    kind = driver.find_element_by_xpath('/html/body/div/div').text.split(' ')[0]
    if kind not in Legal_list:
        print(kind + "��������ȡ��Χ")
        return
    try:
        driver.find_element_by_xpath('html/body/div/div[2]')
        hasPageBar = True
    except:
        hasPageBar = False
    if not hasPageBar:
        ul = driver.find_element_by_xpath('/html/body/div/ul')
        papList = ul.find_elements_by_xpath('li')
        for pap in papList:
            try:
                papname = pap.find_element_by_xpath('a')
                cite.append(papname.text)
            except:
                papname = pap.text
                papname = papname.replace(']',';')
                papname = (papname.split(';'))[1]
                papname = papname[1:-2]
                cite.append(papname)
    else:
        sum_Papers = driver.find_element_by_xpath('/html/body/div[1]/div/b').find_element_by_xpath('span').text
        all = int((int(sum_Papers) - 1) / 10 + 1)
        for ii in range(all):
            driver.find_element_by_xpath('/html/body/div/div[2]/span/a[' + str(ii + 1) + ']').send_keys(Keys.ENTER)
            time.sleep(2)
            ul = driver.find_element_by_xpath('/html/body/div/ul')
            papList = ul.find_elements_by_xpath('li')
            for pap in papList:
                try:
                    papname = pap.find_element_by_xpath('a')
                    cite.append(papname.text)
                except:
                    papname = pap.text
                    papname = papname.replace(']', ';')
                    papname = (papname.split(';'))[1]
                    papname = papname[1:-2]
                    cite.append(papname)
            driver.find_element_by_xpath('/html/body/div/div[2]/span/a[1]').send_keys(Keys.ENTER)
            time.sleep(2)


# ��ȡ���ģ��ж�����������
def getCite_many(driver, cite, sumOfBox):
    for item in range(sumOfBox):
        kind = driver.find_element_by_xpath('html/body/div[' + str(item + 1) + ']/div').text.split(' ')[0]
        if kind not in Legal_list:
            print(kind + '��������ȡ��Χ')
            continue
        try:
            driver.find_element_by_xpath('html/body/div[' + str(item + 1) + ']/div[2]')
            hasPageBar = True
        except:
            hasPageBar = False
        if not hasPageBar:
            ul = driver.find_element_by_xpath('/html/body/div[' + str(item + 1) + ']/ul')
            papList = ul.find_elements_by_xpath('li')
            for pap in papList:
                try:
                    papname = pap.find_element_by_xpath('a')
                    cite.append(papname.text)
                except:
                    papname = pap.text
                    papname = papname.replace(']', ';')
                    papname = (papname.split(';'))[1]
                    papname = papname[1:-2]
                    cite.append(papname)
        else:
            sP = driver.find_element_by_xpath('/html/body/div[' + str(item + 1) + ']/div[1]/b').find_element_by_xpath(
                'span')
            sum_Papers = int(sP.text)
            all = int((sum_Papers - 1) / 10 + 1)
            for ii in range(all):
                driver.find_element_by_xpath(
                    '/html/body/div[' + str(item + 1) + ']/div[2]/span/a[' + str(ii + 1) + ']').send_keys(Keys.ENTER)
                time.sleep(2)
                ul = driver.find_element_by_xpath('/html/body/div[' + str(item + 1) + ']/ul')
                papList = ul.find_elements_by_xpath('li')
                for pap in papList:
                    try:
                        papname = pap.find_element_by_xpath('a')
                        cite.append(papname.text)
                    except:
                        papname = pap.text
                        papname = papname.replace(']', ';')
                        papname = (papname.split(';'))[1]
                        papname = papname[1:-2]
                        cite.append(papname)
                driver.find_element_by_xpath('/html/body/div[' + str(item + 1) + ']/div[2]/span/a[1]').send_keys(
                    Keys.ENTER)
                time.sleep(2)


# �������±���
def washTitle(title):
    title = title.replace(' ', '')
    str = title[-4:]
    if str == "�����׷�":
        title = title[:-4]
    return title


# �����ڵ�
def makeNodes(graph, au, instu, nodes):
    for item in instu:
        if nodes.match('Institution', name=item).first() is None:
            Inst = Node('Institution', name=item)
            graph.create(Inst)
    for item in au:
        if nodes.match('Author', name=item).first() is None:
            Author = Node('Author', name=item)
            graph.create(Author)


# ������ϵ
def makeRelations(graph, pap2au, au2institution, pap2institution, nodes):
    for item in pap2au:
        p = nodes.match('Paper', title=item[0]).first()
        for Author in item[1]:
            au = nodes.match('Author', name=Author).first()
            rel = Relationship(p, "author is", au)
            graph.create(rel)
    for item in au2institution:
        au = nodes.match('Author', name=item[0]).first()
        inst = nodes.match('Institution', name=item[1]).first()
        rel = Relationship(au, 'work in', inst)
        graph.create(rel)
    for item in pap2institution:
        p = nodes.match('Paper', title=item[0]).first()
        inst = nodes.match('Institution', name=item[1]).first()
        rel = Relationship(p, 'function is', inst)
        graph.create(rel)


# ����֪ʶͼ��
def makeGraph(graph, pap, au, instu, pap2au, au2institution, pap2institution):
    graph.create(pap)
    nodes = NodeMatcher(graph)
    for item in instu:
        if nodes.match('Institution', name=item).first() is None:
            Inst = Node('Institution', name=item)
            graph.create(Inst)
    for item in au:
        if nodes.match('Author', name=item).first() is None:
            Author = Node('Author', name=item)
            graph.create(Author)
    # ��ӹ�ϵ
    for item in pap2au:
        p = nodes.match('Paper', title=item[0]).first()
        au = nodes.match('Author', name=item[1]).first()
        rel = Relationship(p, "author is", au)
        graph.create(rel)
    for item in au2institution:
        au = nodes.match('Author', name=item[0]).first()
        inst = nodes.match('Institution', name=item[1]).first()
        rel = Relationship(au, 'work in', inst)
        graph.create(rel)
    for item in pap2institution:
        p = nodes.match('Paper', title=item[0]).first()
        inst = nodes.match('Institution', name=item[1]).first()
        rel = Relationship(p, 'function is', inst)
        graph.create(rel)


# �洢����-������ϵ
def pap_instu_Store(db):
    cur = db.cursor()
    cur.execute("DROP TABLE IF EXISTS paper2institution")
    sql = "create table paper2institution(paperName varchar(30), institution_name varchar(30))"
    cur.execute(sql)
    print("����ɹ�")
    SQLquery = "insert into paper2institution values (%s,%s)"
    for p2i in enumerate(Pap2Institution):
        pai = p2i[1]
        paperName = pai[0]
        institutionName = pai[1]
        value = (paperName, institutionName)
        try:
            cur.execute(SQLquery, value)
            db.commit()
        except pymysql.Error as e:
            print("��������ʧ��")
            print(e)
            db.rollback()


# �洢����-���߹�ϵ
def pap_au_Store(db):
    cur = db.cursor()
    cur.execute("DROP TABLE IF EXISTS paper2au")
    sql = "create table paper2au(paperName varchar(30), author_name varchar(10))"
    cur.execute(sql)
    print("����ɹ�!")
    SQLquery = "insert into paper2au values (%s,%s)"
    for p2a in enumerate(Pap2Au):
        pau = p2a[1]
        paper_name = pau[0]
        author = pau[1]
        value = (paper_name, author)
        try:
            cur.execute(SQLquery, value)
            db.commit()
        except pymysql.Error as e:
            print("�������ݲ���ʧ��")
            print(e)
            db.rollback()


# �洢����-������ϵ
def au_instu_Store(db):
    cur = db.cursor()
    cur.execute("DROP TABLE IF EXISTS au2institution")
    sql = "create table au2institution(author_Name varchar(10), institution_name varchar(30))"
    cur.execute(sql)
    print("����ɹ�!")
    SQLquery = "insert into au2institution values (%s,%s)"
    for a2i in enumerate(Au2Institution):
        aai = a2i[1]
        author_name = aai[0]
        institute = aai[1]
        value = (author_name, institute)
        try:
            cur.execute(SQLquery, value)
            db.commit()
        except pymysql.Error as e:
            print("�������ݲ���ʧ��")
            print(e)
            db.rollback()


# ������
if __name__ == "__main__":
    graph = Graph('bolt://localhost:7687', auth=('neo4j', 'myf021105'))
    graph.delete_all()
    driver = createDriver()
    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '''/html/body/div[2]/div/div[2]/ul/li[4]'''))
    ).click()
    time.sleep(2)
    query = "AF=���ִ�ѧ���ѧԺ OR AF=���ִ�ѧ�������ѧ�뼼��ѧԺ"
    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '''/html/body/div[2]/div/div[2]/div/div[1]/div[1]/div[2]/textarea'''))
    ).send_keys(query)
    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located(
            (By.XPATH, '''/html/body/div[2]/div/div[2]/div/div[1]/div[1]/div[2]/div[2]/input'''))
    ).click()
    res_num = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '''//*[@id="countPageDiv"]/span[1]/em'''))
    ).text
    res_num = int(res_num.replace(',', ''))
    page_num = int(res_num / 20) + 1
    print(f"���ҵ�{res_num}�������{page_num}ҳ")
    # papers_need = res_num
    count = 0
    while count < papers_need:
        time.sleep(1)
        title_list = WebDriverWait(driver, 5).until(EC.presence_of_all_elements_located((By.CLASS_NAME, "fz14")))
        for i in range(len(title_list)):
            pap2au = []
            au2institution = []
            pap2institution = []
            term = i + 1
            time.sleep(0.5)
            try:
                title, authors, source, date, database = getBasicInf(driver, term)
                date = date[0:4]
                title = washTitle(title)
                title_list[i].click()
                au = authors.replace(' ', '').split(';')
                n = driver.window_handles
                driver.switch_to_window(n[-1])
                fund = getFunds(driver)
                domain = getDomain(driver)
                abstract = getAbstract(driver)
                instu = getInstitution(driver)
                matchAuthor2Institution(driver=driver, au=au, instu=instu, au2institution=au2institution)
                matchPap2Institution(title=title, instu=instu, pap2institution=pap2institution)
                matchPaper2Author(au=au, title=title, pap2au=pap2au)
                print("������Ϣ��ȡ���")
                print(title)
                print(au)
                print(instu)
                print(domain)
                print("��ʼ��ȡ����")
                cite = []
                driver.switch_to_frame("frame1")
                time.sleep(3)
                Boxs = driver.find_elements_by_class_name("essayBox")
                sumOfbox = Boxs.__len__()
                if sumOfbox == 1:
                    getCite_1(driver=driver, cite=cite)
                else:
                    getCite_many(driver=driver, cite=cite, sumOfBox=sumOfbox)

                print(len(cite))
                print(cite)
                print("---------------------------------------------------")
                print("-------------------��ʼ����֪ʶͼ��-------------------")
                pap = Node('Paper', title=title, abstract=abstract, source=source, funds=fund, cite_papers=cite, domain=domain, date=date)
                makeGraph(graph=graph, pap=pap, au=au, instu=instu, pap2au=pap2au, pap2institution=pap2institution, au2institution=au2institution)
                driver.switch_to_default_content()
            except:
                print(f"��{count + 1}����ȡʧ��")
                continue
            finally:
                n2 = driver.window_handles
                if len(n2) > 1:
                    driver.close()
                    driver.switch_to_window(n2[0])
                # ����,�ж������Ƿ��㹻
                count += 1
                if count >= papers_need:
                    break
        WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.XPATH, "//a[@id='PageNext']"))).click()

    driver.close()
    print("------------------��ʼ�洢��MySQL------------------")
    try:
        db = pymysql.connect(user=DBUSER, password=DBPASS, host=DBHOST, database=DBNAME)
        au_instu_Store(db=db)
        pap_instu_Store(db=db)
        pap_au_Store(db=db)
    except pymysql.Error as e:
        print("���ݿ�����ʧ�ܣ�ԭ������")
        print(e)

    print("End")
