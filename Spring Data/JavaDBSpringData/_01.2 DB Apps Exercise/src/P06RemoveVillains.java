import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06RemoveVillains {

    private static final String GET_VILLAIN_BY_ID = "select v.name from villains as v where id = ?";
    private static final String NO_SUCH_VILLAIN_MSG = "No such villain was found";

    private static final String GET_MINIONS_COUNT_BY_VILLAINS_ID =
            "select COUNT(mv.minion_id) as m_count from minions_villains as mv where mv.villain_id = ? ";

    private static final String COLUMN_LABEL_MINION_COUNT = "m_count";
    private static final String DELETE_MINIONS_BY_VILLAIN_BY_VILLAIN_ID =
            "delete from minions_villains as mv where mv.villain_id = ?";
    private static final String DELETE_VILLAIN_BY_ID =
            "delete from villains as v where v.id = ?";
    private static final String DELETE_VILLAIN_FORMAT = "%s was deleted%n";
    private static final String DELETE_COUNT_OF_MINIONS_FORMAT = "%d minions was released%n";


    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        int villainId = new Scanner(System.in).nextInt();

        PreparedStatement selectedVillain = connection.prepareStatement(GET_VILLAIN_BY_ID);
        selectedVillain.setInt(1, villainId);
        ResultSet villainSet = selectedVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println(NO_SUCH_VILLAIN_MSG);
            connection.close();
            return;
        }

        String villainName = villainSet.getString(Constants.COLUMN_LABEL_NAME);

        PreparedStatement selectAllMinions = connection.prepareStatement(GET_MINIONS_COUNT_BY_VILLAINS_ID);
        selectAllMinions.setInt(1, villainId);
        ResultSet countMinionsSet = selectAllMinions.executeQuery();
        countMinionsSet.next();

        int countOfDeletedMinions = countMinionsSet.getInt(COLUMN_LABEL_MINION_COUNT);

        connection.setAutoCommit(false);

        try (
                PreparedStatement deleteMinionStatement = connection.prepareStatement(DELETE_MINIONS_BY_VILLAIN_BY_VILLAIN_ID);
                PreparedStatement deleteVillainStatement = connection.prepareStatement(DELETE_VILLAIN_BY_ID);) {

            deleteMinionStatement.setInt(1, villainId);
            deleteMinionStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();

            connection.commit();
            System.out.printf(DELETE_VILLAIN_FORMAT, villainName);
            System.out.printf(DELETE_COUNT_OF_MINIONS_FORMAT, countOfDeletedMinions);

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

        connection.close();

    }

}
