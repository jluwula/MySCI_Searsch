package ChartServlet;

import org.json.JSONArray;
import org.neo4j.driver.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/paperByAuthor")
public class paperByYearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray resource = this.getSource();
        String rdata = resource.toString();
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(rdata);
        resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private JSONArray getSource(){
        JSONArray resource = new JSONArray();
        Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
                AuthTokens.basic("neo4j", "myf021105")
        );
        Session session = driver.session(SessionConfig.forDatabase("sci"));
        String cql = "match(p:Paper)-[r:`author is`]->(a:Author) " +
                "with substring(p.date, 0, 4)as year, a.name as auName " +
                "return year, auName, count(year) as sum";
        Result result = session.run(cql);
        int nowYear = 2023;
        Map<String, Integer> sumPaper = new HashMap<String, Integer>();
        List<Map<String, Integer>> paperByYear = new ArrayList<Map<String, Integer>>();
        for(int i = 0; i < 5; i++){
            Map<String, Integer> map = new HashMap<String, Integer>();
            paperByYear.add(map);
        }

        while(result.hasNext()){
            Record record = result.next();
            if(Integer.parseInt(record.get("year").asString()) >= nowYear - 4){
                int year = Integer.parseInt(record.get("year").asString());
                String name = record.get("auName").asString();
                Integer sum = record.get("sum").asInt();
                sumPaper.merge(name, sum, Integer::sum);
                paperByYear.get(year - nowYear + 4).merge(name, sum, Integer::sum);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(sumPaper.entrySet());

        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        JSONArray title = new JSONArray();
        title.put("作者");
        for(int i = 4; i >= 0; i--){
            title.put(Integer.toString(nowYear - i));
        }
        resource.put(title);

        assert(list.size() >= 10);
        for(int i = 0; i < 10; i++){
            JSONArray authorAndYear = new JSONArray();
            authorAndYear.put(list.get(i).getKey());
            for(int j = 0; j < 5; j++){
                authorAndYear.put(paperByYear.get(j).get(list.get(i).getKey()));
            }
            resource.put(authorAndYear);
        }
        return resource;
    }

}
