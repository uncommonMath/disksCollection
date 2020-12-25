package disks.dao;

public class PersistentException extends Exception {
    public PersistentException(Throwable cause) {
        super(cause);
        cause.printStackTrace();
    }
}
