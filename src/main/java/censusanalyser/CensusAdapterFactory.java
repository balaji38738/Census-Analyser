package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import java.util.Map;

public class CensusAdapterFactory {

    public Map<String, CensusDAO> censusFactory(Analyser.Country country, String... csvFilePath) throws Exceptions{
        if (country.equals(Analyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(country, csvFilePath);
        else if (country.equals(Analyser.Country.US))
            return new USCensusAdapter().loadCensusData(country, csvFilePath);
        return null;
    }
}