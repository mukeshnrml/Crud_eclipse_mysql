package crudOpeation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/crud")
public class updDele extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String action = req.getParameter("action");
        String mail = req.getParameter("mail");

        if ("update".equals(action)) {
            showUpdateform(res, mail);
        } else if ("delete".equals(action)) {
            deleteData(res, mail);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            updateData(req, res);
        }
    }

    private void showUpdateform(HttpServletResponse res, String mail) throws IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        // Your HTML form for update display
        // ...
    }

    private void deleteData(HttpServletResponse res, String mail) throws IOException {
        Connection conn = null;
        PreparedStatement st = null;
        PrintWriter pw = res.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_crud", "root", "");
            String query = "DELETE FROM student WHERE mail=?";
            st = conn.prepareStatement(query);
            st.setString(1, mail);

            int deleted = st.executeUpdate();
            res.setContentType("text/html");
            if (deleted > 0) {
                pw.println("Data has been deleted successfully");
            } else {
                pw.println("Failed to delete data");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateData(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Connection conn = null;
        PreparedStatement st = null;
        PrintWriter pw = res.getWriter();

        String mail = req.getParameter("mail");
        String user = req.getParameter("user");
        String age = req.getParameter("age");
        String phone = req.getParameter("phone");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_crud", "root", "");
            String query = "UPDATE student SET username=?, age=?, phone=? WHERE mail=?";
            st = conn.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, age);
            st.setString(3, phone);
            st.setString(4, mail);

            int updated = st.executeUpdate();
            res.setContentType("text/html");
            if (updated > 0) {
                pw.println("Data has been updated successfully");
            } else {
                pw.println("Failed to update data");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
