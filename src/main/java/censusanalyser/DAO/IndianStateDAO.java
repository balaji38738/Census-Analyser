package censusanalyser.DAO;

import censusanalyser.IndiaCensusCSV;
import censusanalyser.CSVStates;

public class IndianStateDAO {
    public String state;
    public long population;
    public long areaInSqKm;
    public int densityPerSqKm;

    public int srNo;
    public String stateName;
    public int tin;
    public String stateCode;

    public IndianStateDAO(IndiaCensusCSV indianStateCensusCSV) {
        state = indianStateCensusCSV.state;
        population = indianStateCensusCSV.population;
        areaInSqKm = indianStateCensusCSV.areaInSqKm;
        densityPerSqKm = indianStateCensusCSV.densityPerSqKm;
    }

    public IndianStateDAO(CSVStates stateCodeCSV)
    {
        srNo = stateCodeCSV.srNo;
        stateName = stateCodeCSV.stateName;
        tin = stateCodeCSV.tin;
        stateCode = stateCodeCSV.stateCode;
    }
}