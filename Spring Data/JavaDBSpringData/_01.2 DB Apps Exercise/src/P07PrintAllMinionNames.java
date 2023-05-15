import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class P07PrintAllMinionNames {

private static final String GET_MINION_NAMES = "select name from minions";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        PreparedStatement getMinionNames = connection.prepareStatement(GET_MINION_NAMES);
        ResultSet resultSet = getMinionNames.executeQuery();

        List<String> minions = new ArrayList<>();

        while (resultSet.next()) {
            minions.add(resultSet.getString(Constants.COLUMN_LABEL_NAME));
        }

        for (int index = 0; index < minions.size() / 2; index++) {
            System.out.println(minions.get(index));
            System.out.println(minions.get(minions.size() - 1 - index));
        }

        if(minions.size() % 2 != 0) {
            System.out.println(minions.get(minions.size() / 2));
        }

        connection.close();


    }
}
