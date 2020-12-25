package disks.dao;

import java.sql.*;
import java.util.List;

public abstract class AbstractJDBCDao<T extends PersistentObject> implements GenericDao<T> {
    public abstract String getSelectQuery();
    public abstract String getSelectAllQuery();
    public abstract String getCreateQuery();
    public abstract String getUpdateQuery();
    public abstract String getDeleteQuery();
    protected abstract List<T> parseResultSet(ResultSet rs);
    protected abstract boolean prepareStatementForInsert(PreparedStatement statement, T object);
    protected abstract boolean prepareStatementForUpdate(PreparedStatement statement, T object);
    private final Connection connection;

    @Override
    public T selectBy(Integer key) throws PersistentException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.iterator().next();
    }

    @Override
    public List<T> select() throws PersistentException {
        List<T> list;
        String sql = getSelectAllQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
        return list;
    }

    @Override
    public T insert(T object) throws PersistentException {
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (!prepareStatementForInsert(statement, object)) {
                return null;
            }
            statement.executeUpdate();
            var keysSet = statement.getGeneratedKeys();
            if (!keysSet.next()) {
                object.setId(-1);
            } else {
                object.setId(keysSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
        return object;
    }

    @Override
    public boolean update(T object) throws PersistentException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (!prepareStatementForUpdate(statement, object)) {
                return false;
            }
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
    }

    @Override
    public boolean delete(T object) throws PersistentException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                e.printStackTrace();
                throw new PersistentException(e);
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistentException(e);
        }
        return true;
    }

    public AbstractJDBCDao(Connection connection) {
        this.connection = connection;
    }
}
