package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {
    public <T> Iterator<T> getCSVFileIterator(Reader reader, Class<T> csvClass) throws CSVBuilderExceptions, Exceptions {
        return this.getCSVToBean(reader, csvClass).iterator();
    }

    @Override
    public <T> List<T> getCSVFileList(Reader reader, Class<T> csvClass) throws CSVBuilderExceptions, Exceptions {
        CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        return this.getCSVToBean(reader, csvClass).parse();
    }

    private <T> CsvToBean<T> getCSVToBean(Reader reader, Class<T> csvClass) throws CSVBuilderExceptions, Exceptions {
    try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<T>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new Exceptions(e.getMessage(), Exceptions.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
