package censusanalyser.DAO;

import censusanalyser.IndiaCensusCSV;
import censusanalyser.USCensusCSV;

public class CensusDAO {

    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(USCensusCSV next) {
        state = next.State;
        stateCode = next.stateId;
        population = next.Population;
        totalArea = next.totalArea;
        populationDensity = next.populationDensity;
    }

    public CensusDAO(IndiaCensusCSV next) {
        state = next.state;
        population = next.population;
        totalArea = next.areaInSqKm;
        populationDensity = next.densityPerSqKm;
    }
}