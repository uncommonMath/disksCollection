package disks.mysql.JDBCDaoImpl;

import disks.dao.AbstractJDBCDao;
import disks.model.DiskType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlDiskTypeDao extends AbstractJDBCDao<DiskType> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `description` FROM `DiskType` WHERE `id` = ?;";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT `id`, `description` FROM `DiskType`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `DiskType` (`description`) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `DiskType` SET `description` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `DiskType` WHERE `id`= ?;";
    }

    public MySqlDiskTypeDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<DiskType> parseResultSet(ResultSet rs) {
        LinkedList<DiskType> result = new LinkedList<>();
        try {
            while (rs.next()) {
                DiskType informationType = new DiskType();
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
    protected boolean prepareStatementForInsert(PreparedStatement statement, DiskType object) {
        try {
            statement.setString(1, object.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, DiskType object) {
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
