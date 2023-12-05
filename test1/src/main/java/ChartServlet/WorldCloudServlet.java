package ChartServlet;

import Utils.Encoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/WorldCloud")
public class WorldCloudServlet extends HttpServlet {
    public WorldCloudServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray data = this.GetWordCloudWords();
        String rdata = data.toString();
        resp.setContentType("application/json");
        // System.out.println(rdata);
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(rdata);
        resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private JSONArray GetWordCloudWords(){
        JSONArray data = new JSONArray();
        Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
                AuthTokens.basic("neo4j", "myf021105")
        );
        Session session = driver.session(SessionConfig.forDatabase("sci"));
        String cql = "match(n:Paper) unwind n.domain as domain return domain , count(domain) as sum";
        Result result = session.run(cql);
        while(result.hasNext()){
            Record record = result.next();
            String domain = record.get("domain").asString();
            Integer sum = record.get("sum").asInt();
            JSONObject o = new JSONObject();
            o.put("name", domain);
            o.put("value", sum);
            data.put(o);
        }

        session.close();
        driver.close();

        return data;
    }
}
