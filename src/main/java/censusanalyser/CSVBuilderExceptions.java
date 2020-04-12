package censusanalyser;

public class CSVBuilderExceptions extends Throwable{

    enum ExceptionType {
        FILE_PROBLEM,
        UNABLE_TO_PARSE,
    }

    Exceptions.ExceptionType type;

    public CSVBuilderExceptions(String message, Exceptions.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderExceptions(String message, Exceptions.ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
