import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class MainTest {


    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void testInsertUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Test", "TestAddress", "TestEmail");
        User user = Main.selectUser(conn, 1);
        conn.close();
        assertTrue(user.userName.contains("Test"));
    }

    @Test
    public void testSelectUsers() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Test", "TestAddress", "TestEmail");
        Main.insertUser(conn, "Test2", "TestAddress2", "TestEmail2");
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();
        assertTrue(users.size() == 2);

    }

    @Test
    public void testUpdateUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Test", "TestAddress", "TestEmail");
        Main.updateUser(conn, "TestPass", "Pass", "PassEmail", 1);
        User user = Main.selectUser(conn, 1);
        assertTrue(user.userName.contains("Pass"));

    }

    @Test
    public void testDeleteUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Test", "TestAddress", "TestEmail");
        User user = Main.selectUser(conn, 1);
        Main.deleteUser(conn, user.id);
        assertTrue(user.userName.contains(""));
    }
}