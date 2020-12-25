package disks.mysql.JDBCDaoImpl;

import disks.dao.AbstractJDBCDao;
import disks.model.Disk;
import disks.model.DiskType;
import disks.model.InformationType;
import disks.mysql.MySqlDaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlDiskDao extends AbstractJDBCDao<Disk> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `diskType_id`, `title`, `description`, `informationType_id` FROM `Disk` WHERE id = ?;";
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT `id`, `diskType_id`, `title`, `description`, `informationType_id` FROM `Disk`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `Disk` (`diskType_id`, `title`, `description`, `informationType_id`) VALUES (?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `Disk` SET `diskType_id` = ?, `title`  = ?, `description` = ?, `informationType_id` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `Disk` WHERE `id`= ?;";
    }

    public MySqlDiskDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Disk> parseResultSet(ResultSet rs) {
        LinkedList<Disk> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Disk Disk = new Disk();
                Disk.setId(rs.getInt("id"));
                Disk.setDiskType(
                        MySqlDaoFactory.instance.getDao(MySqlDaoFactory.instance.getContext(), DiskType.class).
                                selectBy(rs.getInt("diskType_id"))
                );
                Disk.setTitle(rs.getString("title"));
                Disk.setDescription(rs.getString("description"));
                Disk.setInformationType(
                        MySqlDaoFactory.instance.getDao(MySqlDaoFactory.instance.getContext(), InformationType.class).
                                selectBy(rs.getInt("informationType_id"))
                );
                result.add(Disk);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, Disk object) {
        try {
            int groupId = (object.getInformationType() == null || object.getInformationType().getId() == null) ? -1
                    : object.getInformationType().getId();
            int diskType_id = (object.getDiskType()== null || object.getDiskType().getId() == null) ? -1
                    : object.getDiskType().getId();
            statement.setInt(1, diskType_id);
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setInt(4, groupId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, Disk object) {
        try {
            int groupId = (object.getInformationType() == null || object.getInformationType().getId() == null) ? -1
                    : object.getInformationType().getId();
            int diskType_id = (object.getDiskType()== null || object.getDiskType().getId() == null) ? -1
                    : object.getDiskType().getId();
            statement.setInt(1, diskType_id);
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setInt(4, groupId);
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
