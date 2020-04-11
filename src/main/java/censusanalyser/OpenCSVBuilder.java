package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {

    @Override
    public <T> List<T> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderExceptions {
        try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();
            return csvToBean.parse();
        } catch (IllegalStateException e) {
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
