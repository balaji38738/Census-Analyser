package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;
import java.util.List;

public class Analyser {
    public int loadIndiaCensusData(String csvFilePath) throws Exceptions {

        if(!csvFilePath.contains(".csv"))
            throw new Exceptions("Invalid file type",
                    Exceptions.ExceptionType.INVALID_FILE_TYPE);

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> csvFileList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
                throw new Exceptions(e.getMessage(),
                        Exceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.INVALID_FILE_DELIMITER);
        } catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws Exceptions {
        if(!csvFilePath.contains(".csv"))
            throw new Exceptions("Invalid file type",
                    Exceptions.ExceptionType.INVALID_FILE_TYPE);

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> csvFileList = csvBuilder.getCSVFileList(reader, CSVStates.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("header!"))
                throw new Exceptions(e.getMessage(),
                        Exceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.INVALID_FILE_DELIMITER);
        } catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }
    }

    private <T> int getCount(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        int numOfEntries = (int)StreamSupport.stream(iterable.spliterator(), false).count();
        return numOfEntries;
    }
}
