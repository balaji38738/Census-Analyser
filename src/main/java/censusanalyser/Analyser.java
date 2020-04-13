package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class Analyser {

    List<CensusDAO> csvFileList;
    public enum Country {INDIA, US}
    Map<String, CensusDAO> censusStateMap;
    public Country country;

    public Analyser(Country country) {
        this.country = country;
    }

    public Analyser(Map<String, CensusDAO> censusDAOMap) {

    }

    public Analyser() {
        this.censusStateMap = new HashMap<String, CensusDAO>();
    }

    public int loadCensusData(String... csvFilePath) throws Exceptions{
        censusStateMap = new CensusAdapterFactory().censusFactory (country, csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedData(SortByField.Parameter parameter) throws Exceptions
    {
        if (censusStateMap == null || censusStateMap.size() == 0){
            throw new Exceptions("Data empty", Exceptions.ExceptionType.NO_CENSUS_DATA);
        }
        SortByField sortByField = new SortByField();
        Comparator<CensusDAO> censusComparator = sortByField.getParameter(parameter);
        ArrayList censusDTOS = censusStateMap.values().stream().sorted(censusComparator)
                .map(CensusDAO -> CensusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));

        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }

}