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

@WebServlet("/AddAuthor")//这个地方记得改
public class AddAuthorServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.setCharacterEncoding("UTF-8");
      resp.setCharacterEncoding("UTF-8");
      String name = (String)req.getAttribute("uname");
      String data = this.getAuthorLinks(name);
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

  private String getAuthorLinks(String name){
      Driver driver = GraphDatabase.driver("bolt://121.37.87.37:7687", AuthTokens.basic("neo4j", "myf021105"));
      try (Session session = driver.session(SessionConfig.forDatabase("sci"))) {
        String cql = "CREATE (:Author {name: $authorName})";//CREATE (:Paper {abstract: "我喜欢你", date: “2023-01-15”, domain: ["woxihuanni","nixihuanwo"], funds: ["jlu"],  source: "jluxuebao", title: "我喜欢你"})
        session.run(cql, Values.parameters("authorName", name));
        System.out.println("作者节点已成功添加到数据库中");
        session.close();
        driver.close();
        return "YES";
      } catch (Exception e) {
        System.err.println("添加作者节点时发生错误: " + e.getMessage());
        return "NO";
      }
  }
}
