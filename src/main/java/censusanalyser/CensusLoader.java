package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import censusanalyser.DAO.IndianStateDAO;

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

    }


    public <T> Map loadCensusData(String csvFilePath, Class<T> censusCSVClass) throws Exceptions
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusCSVIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaStateCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .forEach(censusCSV -> indiaCensusCSVFileMap.put(censusCSV.state, new IndianStateDAO(censusCSV)));
                return indiaCensusCSVFileMap;
            }
            else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV"))
            {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusStateMap;
        }catch (IOException e){
            throw  new Exceptions(e.getMessage(),Exceptions.ExceptionType.FILE_PROBLEM);
        }
        catch (NullPointerException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.NO_CENSUS_DATA);
        }catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }catch (RuntimeException e){
            throw new Exceptions(e.getMessage(), Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES);
        }
    }
}
