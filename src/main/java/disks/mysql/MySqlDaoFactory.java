package disks.mysql;

import disks.dao.AbstractDaoFactory;
import disks.dao.GenericDao;
import disks.dao.PersistentObject;
import disks.model.Disk;
import disks.model.DiskType;
import disks.model.InformationType;
import disks.mysql.JDBCDaoImpl.MySqlDiskDao;
import disks.mysql.JDBCDaoImpl.MySqlDiskTypeDao;
import disks.mysql.JDBCDaoImpl.MySqlInformationTypeDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements AbstractDaoFactory<Connection> {
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/disks?useUnicode=true&serverTimezone=UTC&characterEncoding=utf-8";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private final Map<Class<? extends PersistentObject>, DaoCreator<Connection>> creators;

    public static final MySqlDaoFactory instance = new MySqlDaoFactory();

    public Connection getContext() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends PersistentObject> GenericDao<T> getDao(Connection connection, Class<T> persistentClass) {
        var creator = creators.get(persistentClass);
        return (GenericDao<T>)creator.create(connection);
    }

    private MySqlDaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<>();
        creators.put(InformationType.class, MySqlInformationTypeDao::new);
        creators.put(DiskType.class, MySqlDiskTypeDao::new);
        creators.put(Disk.class, MySqlDiskDao::new);
    }
}
