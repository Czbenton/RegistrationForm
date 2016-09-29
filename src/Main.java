import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Zach on 9/29/16.
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");

        createTables(conn);

        Spark.externalStaticFileLocation("public");
        Spark.init();

        Spark.get(
                "/user",
                (request, response) -> {
                    ArrayList<User> users = selectUsers(conn);
                    JsonSerializer s = new JsonSerializer();
                    return s.serialize(users);
                }
        );
        Spark.post(
                "/user",
                (request, response) -> {
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    User user = p.parse(body, User.class);
                    insertUser(conn, user.username, user.address, user.email);
                    return "";
                }
        );
        Spark.put(
                "/user",
                (request, response) -> {
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    User user = p.parse(body, User.class);
                    updateUser(conn, user.id, user.username, user.address, user.email);
                    return "";
                }
        );
        Spark.delete(
                "/user/:id",
                (request, response) -> {
                    Integer id = Integer.parseInt(request.params(":id"));
                    deleteUser(conn, id);
                    return "";
                }
        );
    }

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE if NOT EXISTS users (id IDENTITY , userName VARCHAR , address VARCHAR , eMail VARCHAR ) ");

    }

    public static ArrayList<User> selectUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            Integer id = results.getInt("id");
            String userName = results.getString("username");
            String address = results.getString("address");
            String eMail = results.getString("email");
            users.add(new User(id, userName, address, eMail));
        }
        return users;
    }

    public static void insertUser(Connection conn, String userName, String address, String eMail) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL , ?, ?, ?)");
        stmt.setString(1, userName);
        stmt.setString(2, address);
        stmt.setString(3, eMail);
        stmt.execute();
    }

    public static void updateUser(Connection conn, int id, String userName, String address, String eMail) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE users TABLE SET userName = ?, address=?, eMail=? WHERE id = ?");
        stmt.setString(1, userName);
        stmt.setString(2, address);
        stmt.setString(3, eMail);
        stmt.setInt(4, id);
        stmt.execute();
    }

    public static void deleteUser(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

}
