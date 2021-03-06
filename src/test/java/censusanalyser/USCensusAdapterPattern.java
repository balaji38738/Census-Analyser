package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USCensusAdapterPattern {

    private static final String US_CENSUS_WRONG_DELIMITER = "./src/test/resources/USInvalidDelimiter.csv";
    private static final String US_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/USInvalidHeader.csv";
    private static final String US_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";

    @Test
    public void givenUSCensusData_ThroughCensusAdapter_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDAOMap.size());
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_InIndiaCensusData_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.US, US_CENSUS_WRONG_DELIMITER);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenWrongHeader_InIndiaCensusData_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.US, US_CENSUS_CSV_MISSING_HEADER);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenEmptyCsvFile_ReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.US, US_CENSUS_EMPTY_FILE);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }
}