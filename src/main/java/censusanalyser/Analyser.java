package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;
import censusanalyser.DAO.IndianStateDAO;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.*;
import java.util.stream.StreamSupport;


public class Analyser {

    List<IndianStateDAO> csvFileList;

    public Analyser() {
        this.csvFileList = new ArrayList<IndianStateDAO>();
    }

    public int loadStateCensusData(String csvFilePath) throws Exceptions
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvIterator.hasNext()){
                this.csvFileList.add(new IndianStateDAO(csvIterator.next()));
            }
            return csvFileList.size();
        }catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        }catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }
    }

    public int loadStateCode(String indiaCensusCSVFilePath) throws Exceptions
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> csvFileList = csvBuilder.getCSVFileList(reader, CSVStates.class);
            return csvFileList.size();
        }catch (IOException e) {
            throw new Exceptions(e.getMessage(),
                    Exceptions.ExceptionType.FILE_PROBLEM);
        }catch (CSVBuilderExceptions e) {
            throw new Exceptions(e.getMessage(), e.type.name());
        }
    }

    private <T> int getCount(Iterator<T> iterator){
        Iterable<T> iterable = () -> iterator;
        int nameOfEntries = (int) StreamSupport.stream(iterable.spliterator(),false).count();
        return nameOfEntries;
    }

    public String getStateWiseSortedData(String csvFilePath) throws Exceptions
    {
        loadStateCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0){
            throw new Exceptions("Data empty", Exceptions.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndianStateDAO> censusComparator = Comparator.comparing(IndianStateDAO -> IndianStateDAO.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndianStateDAO> censusComparator){
        for (int iterator = 0; iterator < csvFileList.size(); iterator++){
            for (int innerIterator = 0; innerIterator < csvFileList.size() - iterator -1; innerIterator++){
                IndianStateDAO census1 = csvFileList.get(innerIterator);
                IndianStateDAO census2 = csvFileList.get(innerIterator + 1);

                if (censusComparator.compare(census1, census2) > 0){
                    csvFileList.set(innerIterator, census2);
                    csvFileList.set(innerIterator+1, census1);
                }
            }
        }
    }
}