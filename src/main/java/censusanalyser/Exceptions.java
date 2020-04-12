package censusanalyser;

public class Exceptions extends Exception{

    public Exceptions(String message, String name){

        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    enum ExceptionType {
        FILE_PROBLEM, UNABLE_TO_PARSE, NO_CENSUS_DATA
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