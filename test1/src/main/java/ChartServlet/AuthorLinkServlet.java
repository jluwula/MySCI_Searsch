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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/AuthorLink")
public class AuthorLinkServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject data = this.getAuthorLinks();
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

    private JSONObject getAuthorLinks(){
        JSONObject linksAndSize = new JSONObject();
        JSONArray links = new JSONArray();
        JSONArray nodeSize = new JSONArray();

        Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
                AuthTokens.basic("neo4j", "myf021105")
        );
        Session session = driver.session(SessionConfig.forDatabase("sci"));
        String cql = "match(p1:Paper)-[:`author is`]->(a1:Author)<-[:`author is`]-(p2:Paper)-[:`author is`]->(a2:Author) " +
                "where ID(a1) < ID(a2) " +
                "return DISTINCT  a1.name as source, a2.name as target";
        Result result = session.run(cql);

        Map<String, Integer> map = new HashMap<String, Integer>();

        while(result.hasNext()){
            Record record = result.next();
            JSONObject o = new JSONObject();
            o.put("source", record.get("source").asString());
            o.put("target", record.get("target").asString());
            links.put(o);

            map.merge(record.get("source").asString(), 1, Integer::sum);
            map.merge(record.get("target").asString(), 1, Integer::sum);
        }

        for(String str : map.keySet()){
            JSONObject o = new JSONObject();
            o.put("name", str);
            o.put("symbolSize", map.get(str));
            nodeSize.put(o);
        }
        linksAndSize.put("link", links);
        linksAndSize.put("size", nodeSize);
        session.close();
        driver.close();
        return linksAndSize;
    }
}
