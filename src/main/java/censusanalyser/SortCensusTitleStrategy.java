package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortCensusTitleStrategy {

    static Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    enum Parameter{
        STATE, POPULATION, AREA, DENSITY
    }

    public SortCensusTitleStrategy() {
    }

    public static Comparator getParameter(SortCensusTitleStrategy.Parameter parameter){
        Comparator<IndianStateDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<IndianStateDAO> areaComparator = Comparator.comparing(census -> census.areaInSqKm);
        Comparator<IndianStateDAO> populationComparator = Comparator.comparing(census -> census.population);
        Comparator<IndianStateDAO> densityComparator = Comparator.comparing(census -> census.densityPerSqKm);

        sortParameterComparator.put(Parameter.STATE, stateComparator);
        sortParameterComparator.put(Parameter.AREA, areaComparator);
        sortParameterComparator.put(Parameter.POPULATION, populationComparator);
        sortParameterComparator.put(Parameter.DENSITY, densityComparator);

        Comparator<IndianStateDAO> comparator =sortParameterComparator.get(parameter);
        return comparator;
    }
}