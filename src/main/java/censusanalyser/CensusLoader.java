package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import censusanalyser.DAO.IndianStateDAO;
import java.util.HashMap;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    Map<String, CensusDAO> censusStateMap = null;
    Map<String, IndianStateDAO> indiaCensusCSVFileMap = null;
    public CensusLoader() {
        censusStateMap = new HashMap<>();
    }

    public <T> Map <String, CensusDAO> loadCensusData(Analyser.Country country, String... csvFilePath) throws Exceptions {

        if (country.equals("Analyser.Country.INDIA")) {
            return  this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        }
        else if (country.equals("Analyser.Country.US")) {
            return  this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        }
        else
            throw new Exceptions("Incorrect Country", Exceptions.ExceptionType.INVALID_COUNTRY);
    }

    public <T> Map<String, CensusDAO> loadCensusData(Class<T> censusCSVClass, String... csvFilePath)
            throws Exceptions {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<T> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<T> censusCSVIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaStateCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(),false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV ->censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV ->censusStateMap.put(censusCSV.State, new CensusDAO(censusCSV)));
            }
            if (csvFilePath.length == 1)
            return censusStateMap;
            this.loadStateCode(censusStateMap, csvFilePath[1]);
            return censusStateMap;
        }catch (IOException e) {
            throw new Exceptions(e.getMessage(), Exceptions.ExceptionType.FILE_PROBLEM);
        } catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }catch (NullPointerException e){
            throw new Exceptions(e.getMessage(), Exceptions.ExceptionType.NO_CENSUS_DATA);
        }catch (RuntimeException e){
            throw new Exceptions(e.getMessage(), Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES);
        }
    }

    public int loadStateCode(Map<String, CensusDAO> censusStateMap, String indiaCensusCSVFilePath)
        throws Exceptions {
        try( Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
            Iterable<CSVStates> csvIterable = () -> stateCodeCSVIterator;

            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState ->censusStateMap.get(csvState.stateName)!= null)
                    .forEach(censusCSV ->censusStateMap.get(censusCSV.stateName).state = censusCSV.stateCode);
            return censusStateMap.size();
        }catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        }catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }
    }
}
