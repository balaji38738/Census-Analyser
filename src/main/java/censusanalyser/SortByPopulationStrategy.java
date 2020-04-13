package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;

import java.util.Comparator;

public class SortByPopulationStrategy implements IndianCensusData {
    @Override
    public Comparator censusFileStrategy() {
        return Comparator.<IndianStateDAO, Integer>comparing(census -> Math.toIntExact(census.population));
    }
}