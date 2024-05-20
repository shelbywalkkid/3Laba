import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet ("/login")
public class Login extends HttpServlet {
    public Login() {
    }

    private DataSource ds;
    private void getDataSource() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        ds = (DataSource) envCtx.lookup("jdbc/users");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            getDataSource();
            Connection conn = ds.getConnection();
             pstmt = conn.prepareStatement("SELECT user_id FROM users WHERE login = ? AND password = ? LIMIT 1");
             pstmt.setString(1, login);
             pstmt.setString(2, pass);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // Пользователь найден в базе данных
                response.sendRedirect("welcome.jsp");
            } else {
                // Пользователь не найден
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Перенаправление на страницу ошибки
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/");
    }
}
