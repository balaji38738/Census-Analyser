package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.util.HashMap;
import java.io.Reader;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {

    @Override
    public <T> HashMap<T, T> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderExceptions {
        try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();
            List list = csvToBean.parse();
            HashMap<Integer, Object> map = new HashMap<>();
            Integer count = 0;
            for (Object record : list){
                map.put(count, record);
                count++;
            }
            return (HashMap<T, T>) map;
        }catch (IllegalStateException e){
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.UNABLE_TO_PARSE);
        }
    }

}
