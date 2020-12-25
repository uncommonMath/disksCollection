package disks.dao;

public interface AbstractDaoFactory<Context> {
    interface DaoCreator<Context> {
        <T extends PersistentObject> GenericDao create(Context context);
    }
    Context getContext() throws PersistentException;
    <T extends PersistentObject> GenericDao<T> getDao(Context context, Class<T> dtoClass);
}
