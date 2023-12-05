package LoginServlet;

import POJO.User;
import Utils.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/login")
public class loginServlet extends HttpServlet {
    public loginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDAO userDAO = UserDAO.getInstance("jdbc:mysql://121.37.87.37:3306/science", "root","myf021105");
            String uid = req.getParameter("userId");
            String password = req.getParameter("password");
            User user = userDAO.getUser(uid, password);

            if(user != null){
                HttpSession session = req.getSession();
                session.setAttribute("username", user.getUsername());
                req.getRequestDispatcher("/operation.html").forward(req, resp);
            }else{
                req.getRequestDispatcher("/fail_login.html").forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
