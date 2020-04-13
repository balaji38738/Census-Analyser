package censusanalyser.DAO;

import censusanalyser.Analyser;
import censusanalyser.IndiaCensusCSV;
import censusanalyser.USCensusCSV;

public class CensusDAO {

    public String state;
    public String stateId;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(USCensusCSV next) {
        state = next.State;
        stateId = next.StateId;
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

    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }

    public Object getCensusDTO(Analyser.Country country){
        if (country.equals(Analyser.Country.US))
            return new USCensusCSV(state, stateId, population, populationDensity, totalArea);
        return new IndiaCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }
}