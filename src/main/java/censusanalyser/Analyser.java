package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;


public class Analyser {
    ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
    List<IndiaCensusCSV> stateCensusRecord = null;
    List<CSVStates> stateCodeRecords = null;

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderExceptions
    {
        this.checkValidCSVFile(csvFilePath);
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            stateCensusRecord = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return stateCensusRecord.size();
        }catch (IOException e) {
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.FILE_PROBLEM);
        }catch (RuntimeException e)
        {
            if (e.getMessage().contains("header!"))
                throw new CSVBuilderExceptions(e.getMessage(),
                        CSVBuilderExceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    private void checkValidCSVFile(String csvFilePath) throws CSVBuilderExceptions
    {
        if(!csvFilePath.contains(".csv"))
        {
            throw new CSVBuilderExceptions("Invalid file type",
                    CSVBuilderExceptions.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CSVBuilderExceptions
    {
        this.checkValidCSVFile(csvFilePath);
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            stateCodeRecords = csvBuilder.getCSVFileList(reader, CSVStates.class);
            return stateCodeRecords.size();
        }catch (IOException e) {
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.FILE_PROBLEM);
        }catch (RuntimeException e)
        {
            if (e.getMessage().contains("header!"))
                throw new CSVBuilderExceptions(e.getMessage(),
                        CSVBuilderExceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public String getStateWiseSortedData() throws CSVBuilderExceptions
    {
        if (stateCensusRecord == null || stateCensusRecord.size() == 0){
            throw new CSVBuilderExceptions("Data empty", CSVBuilderExceptions.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(state -> state.state);
        this.sort(censusCSVComparator, stateCensusRecord);
        return new Gson().toJson(stateCensusRecord);
    }

    public <T> void  sort(Comparator<T> censusCSVComparator, List<T> censusRecord)
    {
        for (int iterator = 0; iterator < censusRecord.size()-1; iterator++){
            for (int innerIterator = 0; innerIterator < censusRecord.size()-iterator-1; innerIterator++){
                T census1 = censusRecord.get(innerIterator);
                T census2 = censusRecord.get(innerIterator+1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    censusRecord.set(innerIterator, census2);
                    censusRecord.set(innerIterator+1, census1);
                }
            }
        }
    }

}