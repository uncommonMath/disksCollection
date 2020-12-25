package disks.dao;

import java.util.List;

public interface GenericDao<T extends PersistentObject> {
    List<T> select() throws PersistentException;
    T selectBy(Integer key) throws PersistentException;
    T insert(T object) throws PersistentException;
    boolean update(T object) throws PersistentException;
    boolean delete(T object) throws PersistentException;
}
