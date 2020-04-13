package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest {

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/CensusInvalidHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";


    @Test
    public void givenIndianCensus_ThroughCensusAdapter_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(29, censusDAOMap.size());
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_InIndiaCensusData_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, INDIA_CENSUS_WRONG_DELIMITER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenWrongHeader_InIndiaCensusData_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, INDIA_CENSUS_CSV_MISSING_HEADER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenEmptyCsvFile_ReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> censusDAOMap = censusAdapter.loadCensusData(Analyser.Country.INDIA, WRONG_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenData_WhenReverseSortedByAreaUsingInterface_ShouldReturnSortedOutput(){
        try{
            CensusAdapter censusAdapter = new IndiaCensusAdapter();
            Map<String, CensusDAO> censusDAOMap = new CensusAdapterFactory().censusFactory(Analyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            Analyser analyser = new Analyser(censusDAOMap);
            String sortedCensusData = analyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            CensusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", CsvDataList[0].state);
        } catch (Exceptions e) {
            e.printStackTrace();
        }
    }
}