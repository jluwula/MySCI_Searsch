package OperateGraph;

import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AddPaper")//这个地方记得改
public class AddPaperServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.setCharacterEncoding("UTF-8");
      resp.setCharacterEncoding("UTF-8");
      String abstract_inf = (String)req.getAttribute("abstract");
      String date = (String)req.getAttribute("date");
      String domain_str = (String)req.getAttribute("domain");
      String[] domain_strs = domain_str.split(",");//分割字符串
      List<String> domain = new ArrayList<>();
      for(String s:domain_strs){
        domain.add(s);
      }
      String funds_str = (String)req.getAttribute("funds");
      String[] funds_strs = funds_str.split(",");//分割字符串
      List<String> funds = new ArrayList<>();
      for(String s:funds_strs){
        funds.add(s);
      }
      String source = (String)req.getAttribute("source");
      String title = (String)req.getAttribute("title");
      String data = this.getAuthorLinks(abstract_inf,date,domain,funds,source,title);
      if("YES".equals(data)){
        req.getRequestDispatcher("/addSuccess.html").forward(req, resp);
      }else{
        req.getRequestDispatcher("/addFailure.html").forward(req, resp);
      }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doGet(req, resp);
  }

  private String getAuthorLinks(String abstract_inf,String date,List<String> domain,List<String> funds,String source,String title){
    Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
        AuthTokens.basic("neo4j", "myf021105")
    );
    try (Session session = driver.session(SessionConfig.forDatabase("sci"))) {
      String cql = "CREATE (:Paper {abstract: $paperAbstract, date: $paperDate, domain: $paperDomain, funds: $paperFunds,  source: $paperSource, title: $paperTitle})";
      session.run(cql, Values.parameters(
          "paperAbstract", abstract_inf,
          "paperDate", date,
          "paperDomain", domain,
          "paperFunds", funds,
          "paperSource",source,
          "paperTitle",title
      ));

      System.out.println("论文节点已成功添加到数据库中");
      session.close();
      driver.close();
      return "YES";
    } catch (Exception e) {
      System.err.println("添加论文节点时发生错误: " + e.getMessage());
      return "NO";
    }
  }
}
