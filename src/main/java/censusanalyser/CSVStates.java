package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {
        @CsvBindByName(column = "SrNo", required = true)
        public int srNo;

        @CsvBindByName(column = "State Name", required = true)
        public String stateName;

        @CsvBindByName(column = "TIN", required = true)
        public int tin;

        @CsvBindByName(column = "StateCode", required = true)
        public String stateCode;

        public CSVStates() {

        }

        public CSVStates(int srNo, String stateName, int tin, String stateCode) {
                this.srNo = srNo;
                this.stateName = stateName;
                this.tin = tin;
                this.stateCode = stateCode;
        }

        @Override
        public String toString() {
                return "IndiaStateCodeCSV{" +
                        "srNo=" + srNo +
                        ", stateName='" + stateName + '\'' +
                        ", tin='" + tin + '\'' +
                        ", stateCode='" + stateCode + '\'' +
                        '}';
        }
}