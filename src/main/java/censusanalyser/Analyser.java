package censusanalyser;

import com.google.gson.Gson;
import java.io.File;
import java.nio.file.NoSuchFileException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.*;

public class Analyser {
    ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
    Collection<Object> censusRecord = null;
    HashMap<Object, Object> censusHashMap = null;

    public void checkValidCSVFile(File csvFilePath) throws CSVBuilderExceptions {
        String fileName = csvFilePath.getName();
        String extension = null;
        if (fileName.lastIndexOf(".")!= -1 && fileName.lastIndexOf(".")!= 0)
            extension = fileName.substring(fileName.lastIndexOf(".")+1);
        if(!(extension.equals(".csv"))) {
            throw new CSVBuilderExceptions("Invalid file type",
                    CSVBuilderExceptions.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public int loadStateData(String csvFilePath, Class csvClass) throws CSVBuilderExceptions, IOException {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            censusHashMap = csvBuilder.getCSVFileMap(reader, csvClass);
            return censusHashMap.size();
        }catch (NoSuchFileException e) {
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.FILE_PROBLEM);
        }catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CSVBuilderExceptions(e.getMessage(),
                        CSVBuilderExceptions.ExceptionType.INVALID_FILE_HEADER);
            throw new CSVBuilderExceptions(e.getMessage(),
                    CSVBuilderExceptions.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public String getStateWiseSortedData() throws CSVBuilderExceptions {
        if (censusHashMap == null || censusHashMap.size() == 0){
            throw new CSVBuilderExceptions("Data empty",
                    CSVBuilderExceptions.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(IndiaStateCensusCSV -> IndiaStateCensusCSV.state);
        this.sort(censusCSVComparator, censusHashMap);
        censusRecord = censusHashMap.values();
        return new Gson().toJson(censusRecord);
    }

    public String getStateCodeWiseSortedData() throws CSVBuilderExceptions
    {
        if (censusHashMap == null || censusHashMap.size() == 0){
            throw new CSVBuilderExceptions("Data empty", CSVBuilderExceptions.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStates> codeCSVComparator = Comparator.comparing(CSVStates -> CSVStates.stateCode);
        this.sort(codeCSVComparator, censusHashMap);
        censusRecord = censusHashMap.values();
        return new Gson().toJson(censusRecord);
    }

    public <T> void  sort(Comparator<T> censusCSVComparator, HashMap<Object, Object> censusRecord){
        for (int iterator = 0; iterator < censusRecord.size()-1; iterator++){
            for (int innerIterator = 0; innerIterator < censusRecord.size()-iterator-1; innerIterator++){
                T census1 = (T) censusRecord.get(innerIterator);
                T census2 = (T) censusRecord.get(innerIterator+1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    censusRecord.put(innerIterator, census2);
                    censusRecord.put(innerIterator+1, census1);
                }
            }
        }
    }

}