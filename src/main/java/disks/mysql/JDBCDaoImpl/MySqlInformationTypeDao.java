package disks.mysql.JDBCDaoImpl;

import disks.dao.AbstractJDBCDao;
import disks.model.InformationType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlInformationTypeDao extends AbstractJDBCDao<InformationType> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `description` FROM `InformationType` WHERE `id` = ?;";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT `id`, `description` FROM `InformationType`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `InformationType` (`description`) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `InformationType` SET `description` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `InformationType` WHERE `id`= ?;";
    }

    public MySqlInformationTypeDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<InformationType> parseResultSet(ResultSet rs) {
        LinkedList<InformationType> result = new LinkedList<>();
        try {
            while (rs.next()) {
                InformationType informationType = new InformationType();
                informationType.setId(rs.getInt("id"));
                informationType.setDescription(rs.getString("description"));
                result.add(informationType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, InformationType object) {
        try {
            statement.setString(1, object.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, InformationType object) {
        try {
            statement.setString(1, object.getDescription());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
