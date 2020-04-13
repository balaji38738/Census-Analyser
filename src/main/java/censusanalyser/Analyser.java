package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;


public class Analyser {

    public Country country;

    public Analyser(Country country) {
        this.country = country;
    }

    public enum Country {INDIA, US}

    Map<String, CensusDAO> censusStateMap;

    public int loadCensusData(String... csvFilePath) throws Exceptions{
        censusStateMap = new CensusAdapterFactory().censusFactory (country, csvFilePath);
        return censusStateMap.size();
    }


    public String getFieldWiseSortedData(SortByField.Parameter... parameter) throws Exceptions
    {
        Comparator<CensusDAO> censusComparator = null;
        if (censusStateMap == null || censusStateMap.size() == 0){
            throw new Exceptions("Data empty", Exceptions.ExceptionType.NO_CENSUS_DATA);
        }
        if (parameter.length == 2)
            censusComparator = SortByField.getParameter(parameter[0]).thenComparing(SortByField.getParameter(parameter[1]));
        censusComparator = SortByField.getParameter(parameter[0]);

        ArrayList censusDTOS = censusStateMap.values().stream().sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));

        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }
}