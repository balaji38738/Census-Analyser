package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

public class AnalyserTest {

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
    public void givenCSVFilesReturnsCorrectRecords() {
        try {
            Analyser analyser = new Analyser();
            int censusNumOfRecords = analyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,censusNumOfRecords);
            int stateCodeNumOfRecords = analyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,stateCodeNumOfRecords);
        } catch (Exceptions e) { }
    }

    @Test
    public void givenFileData_WithWrongFile_ShouldThrowException() {
        try {
            Analyser analyser = new Analyser();
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_PATH);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenFileData_WithWrongFileType_ShouldThrowException() {
        try {
            Analyser analyser = new Analyser();
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_TYPE);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_TYPE);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenFileData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            Analyser analyser = new Analyser();
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_DELIMITER);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_DELIMITER);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.INVALID_FILE_DATA, e.type);
        }
    }

    @Test
    public void givenFileData_WithWrongFileHeader_ShouldThrowException() {
        try {
            Analyser analyser = new Analyser();
            analyser.loadIndiaCensusData(WRONG_CENSUS_CSV_FILE_HEADER);
            analyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_HEADER);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.INVALID_FILE_HEADER, e.type);
        }
    }

}
