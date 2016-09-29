import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class MainTest {


    public Connection startConnectionAndTestCreateTabels() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void testInsertAndSelectUsers() throws SQLException {
        Connection conn = startConnectionAndTestCreateTabels();
        Main.insertUser(conn, "Test", "TestAddress", "TestEmail");
        Main.insertUser(conn, "Test2", "TestAddress2", "TestEmail2");
        ArrayList<User> users = Main.selectUsers(conn);
        conn.close();

        assertTrue(users.size() == 2);

    }

    @Test
    public void testUpdateUser() throws SQLException {
        Connection conn = startConnectionAndTestCreateTabels();
        Main.insertUser(conn, "Test", "TestAddress", "TestEmail");
        Main.updateUser(conn, 1, "TestPass", "Pass", "PassEmail");
        ArrayList<User> users = Main.selectUsers(conn);

        assertTrue(users.get(0).getAddress().contains("Pass"));

    }

    @Test
    public void testDeleteUser() throws SQLException {
        Connection conn = startConnectionAndTestCreateTabels();
        Main.insertUser(conn, "Test", "TestAddress", "TestEmail");
        ArrayList<User> users = Main.selectUsers(conn);

        Main.deleteUser(conn, users.get(0).id);
        assertTrue(users.get(0).username.contains(""));
    }
}