package ChartServlet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/PaperByAuthor")
public class PaperByAuthorServlet extends HttpServlet {
    public PaperByAuthorServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject PaperByAuthor = this.getPaperByAuthor();
        String rdata = PaperByAuthor.toString();
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

    private JSONObject getPaperByAuthor(){
        JSONObject paperByAuthor = new JSONObject();
        Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
                AuthTokens.basic("neo4j", "myf021105")
        );
        Session session = driver.session(SessionConfig.forDatabase("sci"));
        String cql = "MATCH(p:Paper)-[:`author is`]->(author:Author) RETURN author.name as name, count(author) as sum";
        Result result = session.run(cql);
        JSONArray authors = new JSONArray();
        JSONArray papers = new JSONArray();
        Map<String, Integer> map = new HashMap<String, Integer>();
        while(result.hasNext()){
            Record record = result.next();
            String author = record.get("name").asString();
            Integer sum = record.get("sum").asInt();
            map.put(author, sum);
        }
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(Map.Entry<String, Integer> entry : sortedEntries){
            authors.put(entry.getKey());
            papers.put(entry.getValue());
        }
        paperByAuthor.put("Authors", authors);
        paperByAuthor.put("SUM", papers);

        session.close();
        driver.close();

        return paperByAuthor;
    }
}
