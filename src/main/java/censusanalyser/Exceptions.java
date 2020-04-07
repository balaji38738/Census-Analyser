package censusanalyser;

public class Exceptions extends Exception {

    enum ExceptionType {
        FILE_PROBLEM,
        INVALID_FILE_TYPE,
        INVALID_FILE_DATA,
        INVALID_FILE_HEADER
    }

    ExceptionType type;

    public Exceptions(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public Exceptions(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
