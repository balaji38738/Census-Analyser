package censusanalyser;

import java.util.HashMap;
import java.io.Reader;
import java.util.List;

public interface ICSVBuilder {
    <T> HashMap<T, T> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderExceptions;
}
