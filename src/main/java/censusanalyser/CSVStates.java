package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {

        @CsvBindByName(column = "SrNo", required = true)
        public int srNo;

        @CsvBindByName(column = "State Name", required = true)
        public String stateName;

        @CsvBindByName(column = "TIN", required = true)
        public String tin;

        @CsvBindByName(column = "StateCode", required = true)
        public String stateCode;

        @Override
        public String toString() {
            return "CSVStates{" +
                    "SrNo='" + srNo + '\'' +
                    ", State Name='" + stateName + '\'' +
                    ", TIN='" + tin + '\'' +
                    ", StateCode='" + stateCode + '\'' +
                    '}';
        }
}
