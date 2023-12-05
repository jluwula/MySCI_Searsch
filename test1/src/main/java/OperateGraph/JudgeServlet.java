package OperateGraph;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Judge")
public class JudgeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String operation = req.getParameter("operation");
        String type = req.getParameter("category");
        if(operation.equals("delete")){
            if(type.equals("author")){
                String uname = req.getParameter("authorName");
                req.setAttribute("uname", uname);
                req.getRequestDispatcher("/DeleteAuthor").forward(req, resp);
            }else if(type.equals("paper")){
                String title = req.getParameter("title");
                req.setAttribute("title", title);
                req.getRequestDispatcher("/DeletePaper").forward(req, resp);
            }else {
                String insName = req.getParameter("institutionName");
                req.setAttribute("insName", insName);
                req.getRequestDispatcher("/DeleteInstitution").forward(req, resp);
            }
        }
        else if(operation.equals("select"))
        {
            if(type.equals("author"))
            {
                String uname = req.getParameter("authorName");
                req.setAttribute("uname", uname);
                req.getRequestDispatcher("/SeekAuthor").forward(req, resp);

            }
            else if(type.equals("paper"))
            {
                String title = req.getParameter("title");
                req.setAttribute("title", title);
                req.getRequestDispatcher("/SeekPaper").forward(req, resp);
            }
            else
            {
                String insName = req.getParameter("institutionName");
                req.setAttribute("insName", insName);
                req.getRequestDispatcher("/SeekInstitution").forward(req, resp);
            }
        }
        else{
            if(type.equals("author"))
            {
                String uname = req.getParameter("authorName");
                req.setAttribute("uname", uname);
                req.getRequestDispatcher("/AddAuthor").forward(req, resp);
            }
            else if(type.equals("paper"))
            {
                String title = req.getParameter("title");
                req.setAttribute("title", title);

                String abs = req.getParameter("abstract");
                req.setAttribute("abstract", abs);

                String funds = req.getParameter("funds");
                req.setAttribute("funds", funds);

                String date = req.getParameter("date");
                req.setAttribute("date", date);

                String domain = req.getParameter("domain");
                req.setAttribute("domain", domain);

                String source = req.getParameter("source");
                req.setAttribute("source", source);

                req.getRequestDispatcher("/AddPaper").forward(req, resp);

            }
            else
            {
                String insName = req.getParameter("institutionName");
                req.setAttribute("insName", insName);
                req.getRequestDispatcher("/AddInstitution").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
