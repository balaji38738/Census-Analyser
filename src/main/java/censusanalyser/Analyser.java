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

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
                throw new Exceptions(e.getMessage(),
                        Exceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws Exceptions {
        if(!csvFilePath.contains(".csv"))
            throw new Exceptions("Invalid file type",
                    Exceptions.ExceptionType.INVALID_FILE_TYPE);

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
                throw new Exceptions(e.getMessage(),
                        Exceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    private <T> int getCount(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        int numOfEntries = (int)StreamSupport.stream(iterable.spliterator(), false).count();
        return numOfEntries;
    }
}
