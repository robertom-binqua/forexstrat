package com.binqua.forexstrat.strategy;

import java.util.ArrayList;
import java.util.List;

public class Report {

    private final List<ReportEntry> entries = new ArrayList<ReportEntry>();

    void addReportEntry(ReportEntry reportEntry) {
        if (reportEntry.getTextToReport().endsWith("\n")) {
            entries.add(reportEntry);
        } else {
            String newText = reportEntry.getTextToReport() + "\n";
            if (reportEntry.isASimpleText()) {
                entries.add(new ReportEntry(newText));
            } else {
                entries.add(new ReportEntry(newText, reportEntry.isALost()));
            }
        }
    }

    public List<ReportEntry> allEntries() {
        return entries;
    }

    public String getCompleteReport() {
        StringBuilder report = new StringBuilder();
        for (ReportEntry reportEntry : allEntries()) {
            report.append(reportEntry.getTextToReport());
        }
        return report.toString();
    }

}
