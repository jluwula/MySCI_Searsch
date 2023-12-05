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

@WebServlet("/rador")
public class radorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject data = this.getRador();
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

    private JSONObject getRador(){
        JSONObject rador = new JSONObject();
        JSONArray domain = new JSONArray();
        JSONArray data = new JSONArray();

        Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
                AuthTokens.basic("neo4j", "myf021105")
        );
        Session session = driver.session(SessionConfig.forDatabase("sci"));
        String cql = "MATCH(p:Paper)-[r:`author is`]->(a:Author) unwind p.domain as domain " +
                "return domain, a.name as name, count(*) as sum";
        Result result = session.run(cql);

        Map<String, Integer> domainMap = new HashMap<>();

        Map<String, Integer> authorPaper = new HashMap<>();

        Map<String, Map<String, Integer>> userDomain = new HashMap<>();

        while(result.hasNext()){
            Record record = result.next();
            domainMap.merge(record.get("domain").asString(), record.get("sum").asInt(), Integer::sum);
            authorPaper.merge(record.get("name").asString(), record.get("sum").asInt(), Integer::sum);
            userDomain.computeIfAbsent(record.get("name").asString(), k -> new HashMap<>());
            userDomain.get(record.get("name").asString()).put(record.get("domain").asString(),record.get("sum").asInt());
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(domainMap.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        List<String> TopAuthor = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for(String str: userDomain.keySet()){
            if(authorPaper.get(str) < 50) continue;
            TopAuthor.add(str);
        }

        for(int i = 0; i < 20; i++){
            int sum = 0;
            for(String uname : TopAuthor){
                sum += userDomain.get(uname).get(list.get(i).getKey()) == null ? 0 : userDomain.get(uname).get(list.get(i).getKey());
            }
            JSONObject o = new JSONObject();
            o.put("name", list.get(i).getKey());
            o.put("value", 10);
            count.add(sum);
            domain.put(o);
        }

        for(String name : TopAuthor){
            JSONObject o = new JSONObject();
            JSONArray array = new JSONArray();
            o.put("name", name);
            for(int i = 0; i < 20; i++){
                int val = userDomain.get(name).get(list.get(i).getKey()) == null ? 0 : userDomain.get(name).get(list.get(i).getKey());
                if(val != 0){
                    val = val * 10 / count.get(i);
                    //if(val > 10) val = 10;
                }
                array.put(val);
            }
            o.put("value", array);
            data.put(o);
        }

        rador.put("domain", domain);
        rador.put("data", data);
        return rador;
    }
}
