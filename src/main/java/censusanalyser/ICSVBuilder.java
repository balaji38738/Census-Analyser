package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public <T> Iterator<T> getCSVFileIterator(Reader reader, Class<T> csvClass) throws Exceptions;
}
