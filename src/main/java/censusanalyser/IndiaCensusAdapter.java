package censusanalyser;

import censusanalyser.DAO.CensusDAO;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter{
    public Map<String, CensusDAO> loadCensusData(Analyser.Country country, String... csvFilePath) throws Exceptions {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndiaStateCode(censusStateMap, csvFilePath[1]);
        return censusStateMap;
    }

    public int loadIndiaStateCode(Map<String, CensusDAO> censusDAOMap, String csvFilePath) throws Exceptions {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> csvStateCode = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
            Iterable<CSVStates> stateCodeCSVS = () -> csvStateCode;

            StreamSupport.stream(stateCodeCSVS.spliterator(), false)
                    .filter(csvState -> censusDAOMap.get(csvState.stateName) != null)
                    .forEach(censusCSV -> censusDAOMap.get(censusCSV.stateName).state = censusCSV.stateCode);
            return censusDAOMap.size();
        } catch (IOException | CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        }
    }
}
