package ChartServlet;

import Utils.ColorUtil;
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

@WebServlet("/insLink")
public class insLinkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject data = this.getInsLinks();
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

    private JSONObject getInsLinks(){
        JSONObject insLinksAndSize = new JSONObject();

        JSONArray links = new JSONArray();
        JSONArray size = new JSONArray();

        Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
                AuthTokens.basic("neo4j", "myf021105")
        );
        Session session = driver.session(SessionConfig.forDatabase("sci"));
        String cql = "match(p1:Paper)-[:`function is`]->(i1:Institution)<-[:`function is`]-(p2:Paper)-[:`function is`]->(i2:Institution) " +
                "where ID(i1) < ID(i2) return DISTINCT i1.name as source, i2.name as target";
        Result result = session.run(cql);
        Map<String, Integer> map = new HashMap<String, Integer>();

        while(result.hasNext()){
            Record record = result.next();
            JSONObject o = new JSONObject();
            JSONObject color = ColorUtil.getColor();
            o.put("source", record.get("source").asString());
            o.put("target", record.get("target").asString());
            o.put("lineStyle", color);
            links.put(o);
            map.merge(record.get("source").asString(), 1, Integer::sum);
            map.merge(record.get("target").asString(), 1, Integer::sum);
        }

        for(String str:map.keySet()){
            JSONObject o = new JSONObject();
            o.put("name", str);
            o.put("symbolSize", map.get(str));
            o.put("itemStyle", ColorUtil.getColor());
            size.put(o);
        }

        insLinksAndSize.put("link", links);
        insLinksAndSize.put("size", size);

        session.close();
        driver.close();
        return insLinksAndSize;

    }
}
