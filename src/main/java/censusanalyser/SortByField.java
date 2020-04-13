package censusanalyser;

import censusanalyser.DAO.IndianStateDAO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    public enum Parameter {

        STATE, POPULATION, AREA, DENSITY;
    }

    public SortByField() {
    }

    public Comparator getParameter(SortByField.Parameter parameter){
        Comparator<IndianStateDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<IndianStateDAO> areaComparator = Comparator.comparing(census -> census.areaInSqKm);
        Comparator<IndianStateDAO> populationComparator = Comparator.comparing(census -> census.population);
        Comparator<IndianStateDAO> densityComparator = Comparator.comparing(census -> census.densityPerSqKm);

        this.sortParameterComparator.put(Parameter.STATE, stateComparator);
        this.sortParameterComparator.put(Parameter.AREA, areaComparator);
        this.sortParameterComparator.put(Parameter.POPULATION, populationComparator);
        this.sortParameterComparator.put(Parameter.DENSITY, densityComparator);

        Comparator<IndianStateDAO> comparator = this.sortParameterComparator.get(parameter);
        return comparator;
    }
}