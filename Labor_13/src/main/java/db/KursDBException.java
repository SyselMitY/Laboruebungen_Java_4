package db;

public class KursDBException extends RuntimeException {
    public KursDBException() {
    }

    public KursDBException(String message) {
        super(message);
    }

    public KursDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public KursDBException(Throwable cause) {
        super(cause);
    }

    public KursDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
