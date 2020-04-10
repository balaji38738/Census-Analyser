package censusanalyser;

public class Exceptions extends Exception {

    enum ExceptionType {
        FILE_PROBLEM,
        INVALID_FILE_TYPE,
        INVALID_FILE_DELIMITER,
        INVALID_FILE_HEADER,
        UNABLE_TO_PARSE;
    }
    ExceptionType type;

    public Exceptions(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public Exceptions(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public Exceptions(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
