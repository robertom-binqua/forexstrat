package com.binqua.forexstrat.strategy.configuration;

import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPair;
import com.binqua.forexstrat.strategy.report.ReportResolution;
import org.junit.Test;

import static com.binqua.forexstrat.strategy.configuration.ReportConfigurationBuilder.aReportConfiguration;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class XmlModifiableConfigurationTest {

    @Test
    public void configurationXmlIsCorrect() {
        ModifiableConfiguration xmlModifiableConfiguration = new XmlModifiableConfiguration();

        xmlModifiableConfiguration.addStrategyStartValue("1");
        xmlModifiableConfiguration.addIsASellStrategy(true);
        xmlModifiableConfiguration.addPipsToBePaidToTheBroker("2");
        xmlModifiableConfiguration.addRetracedToValue(new SelectionConfiguration("3", "4"));
        xmlModifiableConfiguration.addMarketWorstCaseValueConfiguration(new SelectionConfiguration("5", "6"));
        xmlModifiableConfiguration.addCostOfTheSecondCurrencyInPounds("7");
        xmlModifiableConfiguration.addCurrencyPair(EnumBasedCurrencyPair.GBP_USD);
        xmlModifiableConfiguration.addReportConfiguration(aReportConfiguration()
                .withMaxMarketValue("1.300")
                .withMinMarketValue("1.200")
                .withResolutionInPips(ReportResolution.FIVE)
                .build());
        xmlModifiableConfiguration.addEntry(new StrategySingleEntryConfiguration(1, 2.2, new SelectionConfiguration("8", "9")));
        xmlModifiableConfiguration.addEntry(new StrategySingleEntryConfiguration(2, 3.3, new SelectionConfiguration("81", "91")));

        assertThat("configuration xml", xmlModifiableConfiguration.asString(), is(replaceSingleQuotesWithDouble(
                "<trading-configuration>\n" +
                        "    <strategy-start-value value='1'/>\n" +
                        "    <is-a-sell-strategy value='true'/>\n" +
                        "    <pips-to-be-paid-to-the-broker value='2'/>\n" +
                        "    <retraced-to value='4' selection='3'/>\n" +
                        "    <market-worst-case-value value='6' selection='5'/>\n" +
                        "    <cost-of-the-currency-in-pounds value='7'/>\n" +
                        "    <currency-pair value='GBP/USD'/>\n" +
                        "    <strategy-configuration>\n" +
                        "        <strategy-configuration-entry index='1' number-of-contracts='2.2' market-value='9' selection='8'/>\n" +
                        "        <strategy-configuration-entry index='2' number-of-contracts='3.3' market-value='91' selection='81'/>\n" +
                        "    </strategy-configuration>\n" +
                        "    <report-configuration>\n" +
                        "        <maximum-market-value value='1.300'/>\n" +
                        "        <minimum-market-value value='1.200'/>\n" +
                        "        <resolution-in-pips value='5'/>\n" +
                        "    </report-configuration>\n" +
                        "</trading-configuration>\n")));

    }

    private String replaceSingleQuotesWithDouble(String s) {
        return s.replaceAll("'", "\"");
    }
}
