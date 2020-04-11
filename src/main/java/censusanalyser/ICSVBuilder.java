package censusanalyser;

import java.io.Reader;
import java.util.List;

public interface ICSVBuilder {
    <T> List<T> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderExceptions;
}
