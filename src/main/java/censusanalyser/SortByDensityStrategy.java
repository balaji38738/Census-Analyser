package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;

import java.util.Comparator;

public class SortByDensityStrategy implements IndianCensusData {
    @Override
    public Comparator censusFileStrategy() {
        return Comparator.<IndianStateDAO, Integer>comparing(census -> census.densityPerSqKm);
    }
}