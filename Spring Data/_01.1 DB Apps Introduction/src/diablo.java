import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class diablo {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", properties);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        PreparedStatement query = connection.prepareStatement(
                "SELECT user_name, first_name, last_name, " +
                        "(SELECT COUNT(*) from users_games WHERE user_id = u.id) AS games_count " +
                        "FROM users AS u " +
                        "WHERE user_name = ?");
        query.setString(1, username);

        ResultSet result = query.executeQuery();

        if (result.next()) {
            //valid username
            String dbUsername = result.getString("user_name");
            String dbFirst_name = result.getString("first_name");
            String dbLast_name = result.getString("last_name");
            int dbGamesCount = result.getInt("games_count");
            System.out.printf("User: %s%n%s %s has played %d games", dbUsername, dbFirst_name, dbLast_name, dbGamesCount);
        } else {
            //No such user exists
            System.out.println("No such user exists");
        }


    }
}
