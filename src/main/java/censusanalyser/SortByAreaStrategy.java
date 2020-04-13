package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;

import java.util.Comparator;

public class SortByAreaStrategy implements IndianCensusData{

    @Override
    public Comparator censusFileStrategy() {
        return Comparator.<IndianStateDAO, Integer>comparing(census -> Math.toIntExact(census.areaInSqKm));
    }
}