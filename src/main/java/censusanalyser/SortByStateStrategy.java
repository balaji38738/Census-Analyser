package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;

import java.util.Comparator;

public class SortByStateStrategy implements IndianCensusData {
    @Override
    public Comparator censusFileStrategy() {
        return Comparator.<IndianStateDAO, Integer>comparing(census -> Integer.valueOf(census.state));
    }
}