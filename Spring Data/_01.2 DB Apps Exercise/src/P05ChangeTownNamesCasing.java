import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class P05ChangeTownNamesCasing {

    private static final String UPDATE_TOWN_NAMES =
            "UPDATE towns AS t SET name = UPPER(name) WHERE t.country = ?";
    private static final String GET_ALL_TOWN_NAMES_BY_COUNTRY_NAME =
            "SELECT t.name FROM towns AS t WHERE t.country = ?";
    private static String NO_TOWN_AFFECTED_MSG = "No town names were affected.";
    private static String COUNT_OF_AFFECTED_TOWNS_FORMAT = "%d town names were affected.%n";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        String town_name = new Scanner(System.in).nextLine();

        PreparedStatement statement = connection.prepareStatement(UPDATE_TOWN_NAMES);
        statement.setString(1, town_name);

        int updatedCount = statement.executeUpdate();

        if(updatedCount == 0) {
            System.out.println(NO_TOWN_AFFECTED_MSG);
            connection.close();
            return;
        }

        System.out.printf(COUNT_OF_AFFECTED_TOWNS_FORMAT, updatedCount);

        PreparedStatement selectAllTowns = connection.prepareStatement(GET_ALL_TOWN_NAMES_BY_COUNTRY_NAME);
        selectAllTowns.setString(1, town_name);
        ResultSet allTownsResult
                = selectAllTowns.executeQuery();

        ArrayList<String> towns = new ArrayList<>();

        while (allTownsResult.next()) {
            towns.add(allTownsResult.getString(Constants.COLUMN_LABEL_NAME));
        }

        System.out.println(towns);


    }

}

