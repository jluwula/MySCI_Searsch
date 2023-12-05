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

@WebServlet("/Journal")
public class PaperDomainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray data = this.getJournal();
        String rdata = data.toString();
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

    private JSONArray getJournal(){
        JSONArray journal = new JSONArray();
        Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
                AuthTokens.basic("neo4j", "myf021105")
        );
        Session session = driver.session(SessionConfig.forDatabase("sci"));
        String cql = "MATCH(n:Paper) RETURN n.source as source,  count(n.source) as sum";
        Result result = session.run(cql);
        while(result.hasNext()){
            Record record = result.next();
            String source = record.get("source").asString();
            Integer sum = record.get("sum").asInt();
            JSONObject o = new JSONObject();
            o.put("name", source);
            o.put("value", sum);
            journal.put(o);
        }

        session.close();
        driver.close();

        return journal;
    }
}
