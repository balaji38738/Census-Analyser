package censusanalyser;

import censusanalyser.DAO.CensusDAO;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class AnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/CensusInvalidHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";

    @Test
    public void givenIndianCensus_CSVFile_ReturnsCorrectRecords()
    {
        int numOfRecord = 0;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            numOfRecord = analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecord);
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_IndiaCensusData_ShouldThrowCustomExceptionType()
    {
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_WRONG_DELIMITER, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenMissingHeader_IndiaCensusData_ShouldThrowCustomExceptionType()
    {
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_CSV_MISSING_HEADER, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenEmptyCsvFile_ShouldThrowCustomExceptionType()
    {
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_EMPTY_FILE , INDIA_CENSUS_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            analyser.loadCensusData(WRONG_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void ggivenTheStateCensusCSVFile_WhenSortedOnWrongState_ShouldReturnWrongSortedList()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            sortedCensusData = analyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertNotEquals("Goa", censusCSV[0].state);
        }catch(Exceptions e){
            Assert.assertEquals(Exceptions.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnState_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            sortedCensusData = analyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {
        int data = 0;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            data = analyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, data);
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

}


