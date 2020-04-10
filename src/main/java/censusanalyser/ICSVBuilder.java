package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder {
    <T> Iterator<T> getCSVFileIterator(Reader reader, Class<T> csvClass) throws Exceptions, CSVBuilderExceptions;
    <T> List<T> getCSVFileList(Reader reader, Class<T> csvClass) throws CSVBuilderExceptions, Exceptions;
}
