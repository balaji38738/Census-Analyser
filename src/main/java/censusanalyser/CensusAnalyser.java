package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws Exceptions {

        if(!csvFilePath.contains(".csv"))
            throw new Exceptions("Invalid file type",
                    Exceptions.ExceptionType.INVALID_FILE_TYPE);

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
            int namOfEateries = 0;
            Iterable<IndiaCensusCSV> indiaCensusCSVIterable = () -> censusCSVIterator;
            namOfEateries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(), false).count();
            return namOfEateries;
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
            CsvToBeanBuilder<CSVStates> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStates.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStates> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStates> stateCodeCSVIterator = csvToBean.iterator();
            int namOfEateries = 0;
            while (stateCodeCSVIterator.hasNext()) {
                namOfEateries++;
                CSVStates stateCodeData = stateCodeCSVIterator.next();
            }
            return namOfEateries;
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
}
