package censusanalyser.DAO;

import censusanalyser.IndiaCensusCSV;
import censusanalyser.CSVStates;

public class IndianStateDAO {
    public String state;
    public long population;
    public long areaInSqKm;
    public int densityPerSqKm;

    public IndianStateDAO(IndiaCensusCSV indianStateCensusCSV) {
        state = indianStateCensusCSV.state;
        population = indianStateCensusCSV.population;
        areaInSqKm = indianStateCensusCSV.areaInSqKm;
        densityPerSqKm = indianStateCensusCSV.densityPerSqKm;
    }
}