package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AnalyserTest
{
    private static final String INDIA_CSV_STATE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/CensusInvalidHeader.csv";
    private static final String INDIA_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";

    @Test
    public void givenIndianCensus_CSVFile_ReturnsCorrectRecords()
    {
        int numOfRecord = 0;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            numOfRecord = analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            Assert.assertEquals(29, numOfRecord);
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_InIndiaCensusData_ShouldThrowCustomExceptionType()
    {
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_WRONG_DELIMITER, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenMissingHeader_InIndiaCensusData_ShouldThrowCustomExceptionType()
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
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            analyser.loadCensusData(WRONG_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (Exceptions e) {
            Assert.assertEquals(Exceptions.ExceptionType.FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnWrongState_ShouldReturnWrongSortedList()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.STATE);
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
            analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }


    @Test
    public void given_WhenSortedOnState_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("West Bengal", censusCSV[censusCSV.length - 1].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }


    @Test
    public void givenUSCensusDATA_ShouldReturnCorrectRecords() {
        Analyser analyser = new Analyser(Analyser.Country.US);
        int data = 0;
        try {
            data = analyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, data);
        }catch (Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnArea_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.AREA);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[censusCSV.length - 1].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnPopulation_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[censusCSV.length - 1].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnDensity_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.DENSITY);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[censusCSV.length - 1].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnState_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.US);
            analyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", censusCSV[censusCSV.length - 1].State);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnPopulation_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.US);
            analyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[censusCSV.length - 1].State);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnDensity_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.US);
            analyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[censusCSV.length - 1].State);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnArea_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.US);
            analyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[censusCSV.length - 1].State);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenStateHaveSameData_ShouldReturnSortedResultBasedOnDensity()
    {
        String sortedCensusData = null;
        try {
            Analyser analyser = new Analyser(Analyser.Country.INDIA);
            analyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            sortedCensusData = analyser.getFieldWiseSortedData(SortByField.Parameter.POPULATION, SortByField.Parameter.DENSITY);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[censusCSV.length - 1].state);
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }
}