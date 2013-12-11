package com.binqua.forexstrat.strategy;

public class ReportEntry {

    private final Boolean lost;
    private final String textToReport;

    public ReportEntry(String textToReport, Boolean lost) {
        this.textToReport = textToReport;
        this.lost = lost;
    }

    public ReportEntry(String textToReport) {
        this.textToReport = textToReport;
        this.lost = null;
    }

    public Boolean isALost() {
        return lost;
    }

    public boolean isASimpleText() {
        return lost == null;
    }

    public String getTextToReport() {
        return textToReport;
    }

    public String toString() {
        return "textToReport : " + textToReport + " lost : " + lost;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ReportEntry)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        ReportEntry aReportEntry = (ReportEntry) object;
        if (!aReportEntry.getTextToReport().equals(getTextToReport())) {
            return false;
        }
        if (!aReportEntry.isALost().equals(isALost())) {
            return false;
        }
        if (aReportEntry.isASimpleText() != isASimpleText()) {
            return false;
        }
        return true;
    }

}
