package censusanalyser;

import censusanalyser.DAO.CensusDAO;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    public Map<String, CensusDAO> loadCensusData(Analyser.Country country, String... csvFilePath) throws Exceptions
    {
        return this.loadCensusData(USCensusCSV.class, csvFilePath[0]);
    }
}