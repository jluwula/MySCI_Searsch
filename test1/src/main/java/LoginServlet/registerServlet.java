package LoginServlet;

import POJO.User;
import Utils.RandomStringGenerator;
import Utils.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class registerServlet extends HttpServlet {
    public registerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDAO userDAO = UserDAO.getInstance("jdbc:mysql://121.37.87.37:3306/science", "root","myf021105");
            String uid = RandomStringGenerator.generateRandomString();
            while(userDAO.isUserExists(uid)){
                uid = RandomStringGenerator.generateRandomString();
            }
            String uname = req.getParameter("username");
            String password = req.getParameter("password");
            Integer uType = 0;
            User user = new User(uid, uname, password, uType);
            boolean success = userDAO.insertUser(user);

            if(success){
                req.setAttribute("uid", user.getUserID());
                req.getRequestDispatcher("/success_register.jsp").forward(req, resp);
            }else{
                req.getRequestDispatcher("/fail_register.html").forward(req, resp);
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
