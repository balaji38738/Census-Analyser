package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class Analyser {
    public int loadIndiaCensusData(String csvFilePath) throws Exceptions {

        if(!csvFilePath.contains(".csv"))
            throw new Exceptions("Invalid file type",
                    Exceptions.ExceptionType.INVALID_FILE_TYPE);

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaCensusCSV> censusCSVIterator = this.getCSVFileIterator(reader, IndiaCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
                throw new Exceptions(e.getMessage(),
                        Exceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.INVALID_FILE_DATA);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws Exceptions {
        if(!csvFilePath.contains(".csv"))
            throw new Exceptions("Invalid file type",
                    Exceptions.ExceptionType.INVALID_FILE_TYPE);

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<CSVStates> stateCodeCSVIterator = this.getCSVFileIterator(reader, CSVStates.class);
            return this.getCount(stateCodeCSVIterator);
        } catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
                throw new Exceptions(e.getMessage(),
                        Exceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.INVALID_FILE_DATA);
        }
    }

    private <T> Iterator<T> getCSVFileIterator(Reader reader, Class<T> csvClass) throws Exceptions {
        try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<T>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new Exceptions(e.getMessage(), Exceptions.ExceptionType.FILE_PROBLEM);
        }
    }

    private <T> int getCount(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        int numOfEntries = (int)StreamSupport.stream(iterable.spliterator(), false).count();
        return numOfEntries;
    }
}
