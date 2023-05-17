import java.sql.*;
import java.util.Scanner;

public class P09IncreaseAgeStoredProcedure {

    private static final String GET_MINIONS_NAME_AND_AGE_BY_ID =
            "SELECT name, age FROM minions WHERE id = ?";

    private static final String CALL_UPS_GET_OLDER_PROCEDURE =
            "CALL usp_get_older(?)";

    private static final String PRINT_RESULT = "%s %d%n";

    public static void main(String[] args) throws SQLException {

        Connection connection = Utils.getSQLConnection();

        Scanner scanner = new Scanner(System.in);
        int minionId = Integer.parseInt(scanner.nextLine());

        CallableStatement callableStatement = connection.prepareCall(CALL_UPS_GET_OLDER_PROCEDURE);
        callableStatement.setInt(1, minionId);
        callableStatement.executeUpdate();

        printMinionInfo(connection, minionId);

        connection.close();


    }

    private static void printMinionInfo(Connection connection, int minionId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE_BY_ID);

        preparedStatement.setInt(1, minionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String minionName = resultSet.getString(Constants.COLUMN_LABEL_NAME);
        int minionAge = resultSet.getInt("age");
        System.out.printf(PRINT_RESULT, minionName, minionAge);
    }
}
