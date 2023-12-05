package Utils;

import POJO.User;

import java.sql.*;

public class UserDAO {

    private static volatile UserDAO instance;
    private final Connection connection;

    private UserDAO(String url, String username, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(url, username, password);
    }

    public static UserDAO getInstance(String url, String username, String password) throws SQLException {
        if (instance == null) {
            synchronized (UserDAO.class) {
                if (instance == null) {
                    instance = new UserDAO(url, username, password);
                }
            }
        }
        return instance;
    }


    public boolean isUserExists(String userId) {
        try {
            // 使用预编译的 SQL 语句
            String sql = "SELECT COUNT(*) FROM user WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            // 设置参数
            statement.setString(1, userId);

            // 执行查询
            ResultSet resultSet = statement.executeQuery();

            // 获取结果
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // 如果用户存在，返回 true；否则返回 false
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // 出现异常或查询结果为空时，默认用户不存在
    }

    public boolean insertUser(User user) {
        try {
            // 使用预编译的 SQL 语句
            String sql = "INSERT INTO user (UserID, Username, Password, UserType) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // 设置参数
            statement.setString(1, user.getUserID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserType());

            // 执行插入操作
            int rowsAffected = statement.executeUpdate();

            // 返回插入是否成功
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // 出现异常时，默认插入失败
    }

    public User getUser(String userId, String password) {
        try {
            // 使用预编译的 SQL 语句
            String sql = "SELECT * FROM user WHERE UserID = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            // 设置参数
            statement.setString(1, userId);
            statement.setString(2, password);

            // 执行查询
            ResultSet resultSet = statement.executeQuery();

            // 检查是否有结果
            if (resultSet.next()) {
                // 从结果集中获取用户信息
                String username = resultSet.getString("Username");
                int userType = resultSet.getInt("UserType");

                // 创建 User 对象并返回
                return new User(userId, username, password, userType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // 未找到用户时返回 null
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}