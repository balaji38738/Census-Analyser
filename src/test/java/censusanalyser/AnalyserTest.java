package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class AnalyserTest {

    Analyser analyser = new Analyser();

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_TYPE = "./src/test/resources/CensusData.txt";
    private static final String WRONG_CENSUS_CSV_FILE_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String WRONG_CENSUS_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";

    private static final String WRONG_STATE_CODE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_CSV_FILE_TYPE = "./src/test/resources/StateCodeData.txt";
    private static final String WRONG_STATE_CODE_CSV_FILE_DELIMITER = "./src/test/resources/StateInvalidDelimiter.csv";
    private static final String WRONG_STATE_CODE_CSV_FILE_HEADER = "./src/test/resources/StateInvalidHeader.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenCSVFiles_ReturnsCorrectRecords() {
        try {
            int censusNumOfRecords = analyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,censusNumOfRecords);
            int stateCodeNumOfRecords = analyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37, stateCodeNumOfRecords);
        } catch (CSVBuilderExceptions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenFileData_WithWrongFile_ShouldThrowException() {
        try {
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_PATH);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_PATH);
        } catch (CSVBuilderExceptions e) {
            Assert.assertEquals(CSVBuilderExceptions.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenFileData_WithWrongFileType_ShouldThrowException() {
        try {
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_TYPE);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_TYPE);
        } catch (CSVBuilderExceptions e) {
            Assert.assertEquals(CSVBuilderExceptions.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenFileData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_DELIMITER);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_DELIMITER);
        } catch (CSVBuilderExceptions e) {
            Assert.assertEquals(CSVBuilderExceptions.ExceptionType.INVALID_FILE_DELIMITER, e.type);
        }
    }

    @Test
    public void givenFileData_WithWrongFileHeader_ShouldThrowException() {
        try {
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_HEADER);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_HEADER);
        } catch (CSVBuilderExceptions e) {
            Assert.assertEquals(CSVBuilderExceptions.ExceptionType.INVALID_FILE_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnState_ShouldReturnSortedList() throws CSVBuilderExceptions
    {
        analyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH );
        String sortedCensusData = analyser.getStateWiseSortedData();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        Assert.assertEquals("West Bengal" , censusCSV[28].state);
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenSortedOnStateCode_ShouldReturnSortedList() throws CSVBuilderExceptions
    {
        analyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
        String sortedStateCodeData = analyser.getStateCodeWiseSortedData();
        CSVStates[] censusCSV = new Gson().fromJson(sortedStateCodeData, CSVStates[].class);
        Assert.assertEquals("AD", censusCSV[0].stateCode);
        Assert.assertEquals("WB" , censusCSV[36].stateCode);
    }
}
