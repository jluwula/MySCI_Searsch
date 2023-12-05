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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/SeekAuthor")
public class SeekAuthorServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    String name = (String)req.getAttribute("uname");
    JSONObject data = this.getAuthorLinks(name);
    String rdata = data.toString();
    req.setAttribute("jsonObj", rdata);
    req.getRequestDispatcher("/SeekGraph.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doGet(req, resp);
  }

  private JSONObject getAuthorLinks(String name){
    JSONObject linksAndSize = new JSONObject();
    JSONArray links = new JSONArray();
    JSONArray nodeSize = new JSONArray();

    Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687",
        AuthTokens.basic("neo4j", "myf021105")
    );
    Session session = driver.session(SessionConfig.forDatabase("sci"));
    String cql = "match(p1:Paper)-[:`author is`]->(a1:Author) " +
        "where a1.name = $authorName " +
        "return DISTINCT  p1.title as source, a1.name as target";
    Result result = session.run(cql,Values.parameters("authorName", name));
    String cql1 = "match(i1:Institution)<-[:`work in`]-(a1:Author) " +
        "where a1.name = $authorName " +
        "return DISTINCT  a1.name as source,  i1.name as target";
    Result result1 = session.run(cql1,Values.parameters("authorName", name));
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
    while(result1.hasNext()){
      Record record = result1.next();
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
