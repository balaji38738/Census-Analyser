package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

public class StateAnalyserTest {
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/CensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/StateInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/StateInvalidHeader.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_TYPE);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_DELIMITER);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.INVALID_FILE_DATA, e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_HEADER);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.INVALID_FILE_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (Exceptions e) { }
    }
}

