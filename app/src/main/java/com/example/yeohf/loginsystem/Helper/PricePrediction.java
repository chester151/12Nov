package com.example.yeohf.loginsystem.Helper;

public class PricePrediction {
    static String[] town_list = {"ANG MO KIO", "BEDOK", "BISHAN", "BUKIT BATOK", "BUKIT	MERAH", "BUKIT PANJANG", "BUKIT TIMAH", "CENTRAL AREA", "CHOA CHU KANG", "CLELMENTI", "GEYLANG", "HOUGANG", "JURONG EAST", "JURONG WEST", "KALLANG/WHAMPOA", "MARINE PARADE", "PASIR RIS", "PUNGGOL", "QUEENSTOWN", "SEMBAWANG", "SENGKANG", "SERANGOON", "TAMPINES", "TOA PAYOH", "WOODLANDS", "YISHUN"};
    static String[] flat_type_list = {"1 ROOM", "2 ROOM", "3 ROOM", "4 ROOM", "5 ROOM", "EXECUTIVE", "MULTI-GENERATION"};
    static String[] storey_list = {"01 TO 03", "04 TO 06", "07 TO 09", "10 TO 12", "13 TO 15", "16 TO 18", "19 TO 21", "22 TO 24", "25 TO 27", "28 TO 30", "31 TO 33", "34 TO 36", "37 TO 39", "40 TO 42", "43 TO 45", "46 TO 48", "49 TO 51"};
    static String[] flat_model_list = {"Adjoined flat", "Apartment", "DBSS", "Improved", "Improved-Maisonette", "Maisonette", "Model A", "Model A-Maisonette", "Model A2", "Multi Generation", "New Generation", "Premium Apartment", "Premium Apartment Loft", "Premium Maisonette", "Simplified", "Standard", "Terrace", "Type S1", "Type S2"};
    public String town;
    public String flat_type;
    public String storey;
    public String flat_model;
    public int area;

   /* public static void main(String[] args) {
        PricePrediction housing = new PricePrediction("BEDOK", "3 ROOM", "19 TO 21", "Improved", 80);
        //System.out.println("The estimated price is:" + housing.getPrice());
    }*/

    public PricePrediction(String town, String flat_type, String storey, String flat_model, int area) {
        this.town = town;
        this.flat_type = flat_type;
        this.storey = storey;
        this.flat_model = flat_model;
        this.area = area;
    }

    public double getTownIndex(String town) {
        switch (town) {
            case "BEDOK":
                return -153.87;
            case "BISHAN":
                return 734.99;
            case "BUKIT BATOK":
                return -771.66;
            case "BUKIT	MERAH":
                return 1338.9;
            case "BUKIT PANJANG":
                return -1131.46;
            case "BUKIT TIMAH":
                return 1531.51;
            case "CENTRAL AREA":
                return 1527.904;
            case "CHOA CHU KANG":
                return -1476.374;
            case "CLEMENTI":
                return 461.77;
            case "GEYLANG":
                return 364.668;
            case "HOUGANG":
                return -718.826;
            case "JURONG EAST":
                return -519.911;
            case "JURONG WEST":
                return -1010.895;
            case "KALLANG/WHAMPOA":
                return 592.504;
            case "MARINE PARADE":
                return 1473.542;
            case "PASIR RIS":
                return -1027.489;
            case "PUNGGOL":
                return -584.830;
            case "QUEENSTOWN":
                return 1455.385;
            case "SEMBAWANG":
                return -1286.514;
            case "SENGKANG":
                return -806.496;
            case "SERANGOON":
                return -4.496;
            case "TAMPINES":
                return -543.006;
            case "TOA PAYOH":
                return 324.341;
            case "WOODLANDS":
                return -1296.257;
            case "YISHUN":
                return -951.937;
            default:
                return 0;
        }
    }

    public double getTypeIndex(String room_type) {
        switch (room_type) {
            case "2 ROOM":
                return -118.386;
            case "3 ROOM":
                return -672.339;
            case "4 ROOM":
                return -642.992;
            case "5 ROOM":
                return -565.901;
            case "EXECUTIVE":
                return -749.295;
            case "MULTI-GENERATION":
                return -396.903;
            default:
                return 0.0;
        }
    }

    public double getStoreyIndex(String storey_range) {
        switch (storey_range) {
            case "01 TO 03":
                return 145.077;
            case "04 TO 06":
                return 179.069;
            case "07 TO 09":
                return 291.658;
            case "10 TO 12":
                return 383.443;
            case "13 TO 15":
                return 554.742;
            case "16 TO 18":
                return 792.094;
            case "19 TO 21":
                return 1145.515;
            case "22 TO 24":
                return 1351.440;
            case "25 TO 27":
                return 1645.022;
            case "28 TO 30":
                return 1959.337;
            case "31 TO 33":
                return 2115.544;
            case "34 TO 36":
                return 2298.197;
            case "37 TO 39":
                return 2264.721;
            case "40 TO 42":
                return 2296.266;
            case "43 TO 45":
                return 2197.140;
            case "46 TO 48":
                return 2165.377;
            case "49 TO 51":
                return 2390.237;
            default:
                return 0.0;
        }
    }

    public double getModelIndex(String flat_model) {
        switch (flat_model) {
            case "Adjoined flat":
                return -589.754;
            case "Apartment":
                return 102.726;
            case "DBSS":
                return 1619.109;
            case "Improved":
                return -444.559;
            case "Improved-Maisonette":
                return 709.988;
            case "Maisonette":
                return 143.518;
            case "Model A":
                return -118.825;
            case "Model A-Maisonette":
                return 43.726;
            case "Model A2":
                return -88.805;
            case "Multi Generation":
                return -51.335;
            case "New Generation":
                return -375.163;
            case "Premium Apartment":
                return -21.141;
            case "Premium Apartment Loft":
                return 209.312;
            case "Premium Maisonette":
                return 460.698;
            case "Simplified":
                return -124.445;
            case "Standard":
                return -806.730;
            case "Terrace":
                return 1999.565;
            case "Type S1":
                return 1470.460;
            case "Type S2":
                return 1525.315;
            default:
                return 0.0;
        }
    }

    public double getPrice() {
        return (5509.230 + getTownIndex(town) + getTypeIndex(flat_type) + getStoreyIndex(storey) + getModelIndex(flat_model)) * 80;
    }
}