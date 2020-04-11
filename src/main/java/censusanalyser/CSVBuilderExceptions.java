package censusanalyser;

public class CSVBuilderExceptions extends Exception{

    enum ExceptionType {
        FILE_PROBLEM,
        INVALID_FILE_TYPE,
        INVALID_FILE_DELIMITER,
        INVALID_FILE_HEADER,
        UNABLE_TO_PARSE,
        NO_CENSUS_DATA;
    }

    ExceptionType type;

    public CSVBuilderExceptions(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderExceptions(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
