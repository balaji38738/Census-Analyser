package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CSV_STATE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/CensusInvalidHeader.csv";
    private static final String INDIA_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";

    @Test
    public void givenIndiaCensusData_ThroughCensusAdapter_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            Assert.assertEquals(29, censusDAOMap.size());
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_ThroughCensusAdapter_ShouldReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, INDIA_CENSUS_WRONG_DELIMITER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenMissingHeader_ThroughCensusAdapter_ShouldReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, INDIA_CENSUS_CSV_MISSING_HEADER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenEmptyCSVFile_ThroughCensusAdapter_ShouldReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, INDIA_CENSUS_EMPTY_FILE, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }
}