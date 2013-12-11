package com.binqua.forexstrat.strategy.report;


public enum ReportResolution {

    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY_FIVE(25);

    private int incrementInPips;

    ReportResolution(int incrementInPips) {
        this.incrementInPips = incrementInPips;
    }

    public int incrementInPips() {
        return incrementInPips;
    }

    public static ReportResolution resolutionFrom(String resolutionToFind) {
        for (ReportResolution resolutionCandidate : values()) {
            if (resolutionToFind.equals(String.valueOf(resolutionCandidate.incrementInPips()))) {
                return resolutionCandidate;
            }
        }
        throw new IllegalArgumentException("No resolution found for " + resolutionToFind + " .Values available are " + values());
    }

    public static String[] displayValues() {
        String[] displayValues = new String[values().length];
        for (int i = 0; i < displayValues.length; i++) {
            displayValues[i] = String.valueOf(values()[i].incrementInPips());
        }
        return displayValues;
    }
}
