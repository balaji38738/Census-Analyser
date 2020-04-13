package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class AnalyserTest {

    Analyser analyser = new Analyser();

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/CensusInvalidHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";

    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowCustomExceptionType()
    {
        try {
            analyser.loadStateCensusData(INDIA_CENSUS_WRONG_DELIMITER);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenIndianCensus_CSVFile_ReturnsCorrectRecords()
    {
        try {
            int numOfRecord = analyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecord);
        }catch (Exceptions e){  }
    }

    @Test
    public void givenIndiaCensusData_WithMissingHeader_ShouldThrowCustomExceptionType()
    {
        try {
            analyser.loadStateCensusData(INDIA_CENSUS_CSV_MISSING_HEADER);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenUSCENSUSDATA_ShouldReturnCorrectRecords(){
        int data = 0;
        try {
            data = analyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, data);
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_CSVFile_ReturnCorrectRecords()
    {
        try {
            int numOfStateCodes = analyser.loadStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfStateCodes);
        } catch (Exceptions e)
        {
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            analyser.loadStateCensusData(WRONG_CENSUS_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnState_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            sortedCensusData = analyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnWrongState_ShouldReturnWrongSortedList()
    {
        String sortedCensusData = null;
        try {
            sortedCensusData = analyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertNotEquals("Goa", censusCSV[0].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }
}
