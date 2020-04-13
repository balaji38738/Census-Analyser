package censusanalyser;

public class Exceptions extends Exception{

    public Exceptions(String message, String name){

        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    enum ExceptionType {
        FILE_PROBLEM, UNABLE_TO_PARSE, INVALID_COUNTRY,
        NO_CENSUS_DATA, CSV_FILE_INTERNAL_ISSUES,
    }

    ExceptionType type;

    public Exceptions(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public Exceptions(String message, ExceptionType type, Throwable cause){

        super(message, cause);
        this.type = type;
    }
}