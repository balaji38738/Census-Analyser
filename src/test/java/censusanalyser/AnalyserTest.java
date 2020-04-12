package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class AnalyserTest {

    Analyser analyser = new Analyser();

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensus_CSVFile_ReturnsCorrectRecords()
    {
        try {
            int numOfRecord = analyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecord);
        }catch (Exceptions e){  }
    }

    @Test
    public void givenIndianStateCode_CSVFile_ReturnCorrectRecords()
    {
        try {
            int numOfStateCodes = analyser.loadStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfStateCodes);
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
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
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
