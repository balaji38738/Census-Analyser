package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;
import censusanalyser.DAO.CensusDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class Analyser {

    List<CensusDAO> csvFileList;
    Map<String, CensusDAO> csvFileMap;
    Map<String, IndianStateDAO> indiaCensusCSVFileMap;
    Map<String, IndianStateDAO> indiaStateCodeCSVFileMap;

    public Analyser() {
        this.csvFileMap = new HashMap<String, CensusDAO>();
    }

    public int loadStateCensusData(String csvFilePath) throws Exceptions{
        try {
        indiaCensusCSVFileMap = new CensusLoader().loadCensusData(csvFilePath, IndiaCensusCSV.class);
        return indiaCensusCSVFileMap.size();
        }catch (NullPointerException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);}
    }

    public int loadStateCode(String indiaCensusCSVFilePath) throws Exceptions
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStates.class);
            Iterable<CSVStates> censusDAOIterable = () -> censusCSVIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(),false)
                    .forEach(censusCSV ->indiaStateCodeCSVFileMap.put(censusCSV.stateName, new IndianStateDAO(censusCSV)));
            return indiaStateCodeCSVFileMap.size();
        }catch (IOException e){
            throw  new Exceptions(e.getMessage(),Exceptions.ExceptionType.FILE_PROBLEM);
        }
        catch (NullPointerException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        }catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }catch (RuntimeException e){
            throw new Exceptions(e.getMessage(), Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES);
        }

    }

    public String getStateWiseSortedData(String csvFilePath) throws Exceptions
    {
        if (csvFileMap == null || csvFileMap.size() == 0){
            throw new Exceptions("Data empty", Exceptions.ExceptionType.NO_CENSUS_DATA);
        }
        Map<String, Comparator<CensusDAO>> comparatorMap = new HashMap<String, Comparator<CensusDAO>>();
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        comparatorMap.put("state", Comparator.comparing(census -> census.state));
        List<CensusDAO> censusDAOS = csvFileMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    public String getUSPopulationWiseSortedData(String csvFilePath) throws Exceptions
    {
        if (csvFileMap == null || csvFileMap.size() == 0){
            throw new Exceptions("Data empty", Exceptions.ExceptionType.NO_CENSUS_DATA);
        }
        Map<String, Comparator<IndianStateDAO>> comparatorMap = new HashMap<String, Comparator<IndianStateDAO>>();
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        comparatorMap.put("state", Comparator.comparing(census -> census.state));
        List<CensusDAO> censusDAOS = csvFileMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusComparator)
            throws Exceptions {
        for (int iterator = 0; iterator < censusDAOS.size(); iterator++){
            for (int innerIterator = 0; innerIterator < censusDAOS.size() - iterator -1; innerIterator++){
                CensusDAO census1 = censusDAOS.get(innerIterator);
                CensusDAO census2 = censusDAOS.get(innerIterator + 1);

                if (censusComparator.compare(census1, census2) < 0){
                    censusDAOS.set(innerIterator, census2);
                    censusDAOS.set(innerIterator + 1, census1);
                }
            }
        }
    }

    public int loadUSCensusData(String usCensusCsvFilePath) throws Exceptions {
        csvFileMap = new CensusLoader().loadCensusData(usCensusCsvFilePath, USCensusCSV.class);
        return csvFileMap.size();
    }
}