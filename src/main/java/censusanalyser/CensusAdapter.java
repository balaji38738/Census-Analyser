package censusanalyser;

import censusanalyser.DAO.CensusDAO;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {
    Map<String, CensusDAO> censusDAOMap = new HashMap<>();

    public abstract Map <String, CensusDAO> loadCensusData(Analyser.Country country, String... csvFilePath) throws Exceptions;

    public <T> Map loadCensusData(Class<T> censusCSVClass, String csvFilePath) throws Exceptions {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<T> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<T> censusDAOIterable = () -> censusCSVIterator;

            if (censusCSVClass.getName().equals("Analyser.IndiaStateCensusCSV")) {
                StreamSupport.stream(censusDAOIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("Analyser.USCensusCSV")) {
                StreamSupport.stream(censusDAOIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.State, new CensusDAO(censusCSV)));
            }
            return censusDAOMap;
        } catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES);
        } catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }
    }
} 