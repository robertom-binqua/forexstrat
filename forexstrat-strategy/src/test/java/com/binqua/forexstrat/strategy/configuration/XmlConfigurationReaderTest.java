package com.binqua.forexstrat.strategy.configuration;


import com.binqua.forexstrat.feedreader.core.model.impl.EnumBasedCurrencyPairs;
import com.binqua.forexstrat.strategy.report.ReportResolution;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.IOException;

import static com.binqua.forexstrat.strategy.configuration.ReportConfigurationBuilder.aReportConfiguration;
import static com.binqua.forexstrat.strategy.configuration.StrategyConfigurationBuilder.aStrategyConfiguration;
import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.FileUtils.write;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class XmlConfigurationReaderTest {

    private static final String CONFIGURATION_NAME = "this is my configuration";
    private final File configurationFileUnderTest = new File(System.getProperty("java.io.tmpdir"), XmlConfigurationReaderTest.class.getName() + "configurationFile.xml");

    @Before
    public void setUp() {
        deleteQuietly(configurationFileUnderTest);
    }

    @Test
    public void configurationCanBeReadFromAXmlFile() throws IOException {
        ConfigurationReader xmlConfigurationReader = new XmlConfigurationReader(createAConfigurationFileWithContent(aValidConfiguration()).getAbsoluteFile(), new EnumBasedCurrencyPairs());
        assertThatConfigurationContentIsCorrect(xmlConfigurationReader);
    }

    @Test
    public void asStringReturnsTheCorrectConfiguration() throws IOException {
        ConfigurationReader xmlConfigurationReader = new XmlConfigurationReader(createAConfigurationFileWithContent(aValidConfiguration()).getAbsoluteFile(), new EnumBasedCurrencyPairs());
        assertEquals(aValidConfiguration(), xmlConfigurationReader.asString());
    }

    @Test
    public void configurationReaderExceptionIsThrownIfFileContentIsNotAValidXml() throws IOException {
        try {
            new XmlConfigurationReader(createAConfigurationFileWithContent(aNonValidXmlConfiguration()).getAbsoluteFile(), new EnumBasedCurrencyPairs());
            fail(ConfigurationReaderException.class + " should have been thrown");
        } catch (ConfigurationReaderException cre) {
            assertThat(cre.getCause(), (Matcher<? super Throwable>) is(SAXParseException.class));
        }
    }

    @Test
    public void configurationCanBeReadFromAXmlString() throws IOException {
        ConfigurationReader xmlConfigurationReader = new XmlConfigurationReader(aValidConfiguration(), new EnumBasedCurrencyPairs());
        assertThatConfigurationContentIsCorrect(xmlConfigurationReader);
    }

    @Test
    public void configurationReaderExceptionIsThrownIfContentIsNotAValidXml() throws IOException {
        try {
            new XmlConfigurationReader(aNonValidXmlConfiguration(), new EnumBasedCurrencyPairs());
            fail(ConfigurationReaderException.class + " should have been thrown");
        } catch (ConfigurationReaderException cre) {
            assertThat(cre.getCause(), (Matcher<? super Throwable>) is(SAXParseException.class));
        }
    }

    private String aNonValidXmlConfiguration() {
        return "<npn valid xml>";
    }

    private void assertThatConfigurationContentIsCorrect(ConfigurationReader xmlConfigurationReader) {
        assertThat("name", xmlConfigurationReader.name(), is(CONFIGURATION_NAME));
        assertThat("currency-pair", xmlConfigurationReader.getCurrencyPair().asString(), is("EUR/USD"));
        assertThat("strategy-start-value", xmlConfigurationReader.wholeStrategyEntryValue(), is("1.4300"));
        assertThat("is-a-sell-strategy", xmlConfigurationReader.isASellingStrategy(), is(true));
        assertThat("pips-to-be-paid-to-the-broker", xmlConfigurationReader.getPipsToBePaidToTheBroker(), is("2"));
        assertThat("retraced-to", xmlConfigurationReader.getRetracedToConfiguration(), is(new SelectionConfiguration("300", "1.4300")));
        assertThat("market-worst-case-value", xmlConfigurationReader.getMarketWorstValueConfiguration(), is(new SelectionConfiguration("100", "1.4500")));
        assertThat("cost of the currency", xmlConfigurationReader.getCostOfTheRightCurrencyInPounds(), is("0.8839"));
        assertThat("cost of the currency", xmlConfigurationReader.getStrategyConfiguration(), is(
                aStrategyConfiguration()
                        .withSingleStrategyConfiguration(new StrategySingleEntryConfiguration(0, 0.15, new SelectionConfiguration("300", "1.4300")))
                        .withSingleStrategyConfiguration(new StrategySingleEntryConfiguration(1, 0.3, new SelectionConfiguration("265", "1.4335")))
                        .withSingleStrategyConfiguration(new StrategySingleEntryConfiguration(2, 1.0, new SelectionConfiguration("213", "1.4387")))
                        .build()
        ));
        assertThat("report configuration", xmlConfigurationReader.getReportConfiguration(), is(aReportConfiguration()
                .withMaxMarketValue("1.300")
                .withMinMarketValue("1.200")
                .withResolutionInPips(ReportResolution.TWO).build()));
        assertThat("actual market value", xmlConfigurationReader.actualMarketValue(), is("1111"));
    }

    private String aValidConfiguration() {
        return replaceSingleQuotesWithDouble("<trading-configuration>\n" +
                "    <name value='" + CONFIGURATION_NAME + "'/>\n" +
                "    <currency-pair value='EUR/USD'/>\n" +
                "    <strategy-start-value value='1.4300'/>\n" +
                "    <is-a-sell-strategy value='true'/>\n" +
                "    <pips-to-be-paid-to-the-broker value='2'/>\n" +
                "    <retraced-to value='1.4300' selection='300'/>\n" +
                "    <market-worst-case-value value='1.4500' selection='100'/>\n" +
                "    <cost-of-the-currency-in-pounds value='0.8839'/>\n" +
                "    <actual-market-value value='1111'/>\n" +
                "    <strategy-configuration>\n" +
                "        <strategy-configuration-entry index='0' number-of-contracts='0.15' market-value='1.4300' selection='300'/>\n" +
                "        <strategy-configuration-entry index='1' number-of-contracts='0.3' market-value='1.4335' selection='265'/>\n" +
                "        <strategy-configuration-entry index='2' number-of-contracts='1.0' market-value='1.4387' selection='213'/>\n" +
                "    </strategy-configuration>\n" +
                "    <report-configuration>\n" +
                "        <maximum-market-value value='1.300'/>\n" +
                "        <minimum-market-value value='1.200'/>\n" +
                "        <resolution-in-pips value='2'/>\n" +
                "    </report-configuration>\n" +
                "</trading-configuration>");
    }

    private String replaceSingleQuotesWithDouble(String s) {
        return s.replaceAll("'", "\"");
    }


    private File createAConfigurationFileWithContent(String fileContent) throws IOException {
        write(configurationFileUnderTest, fileContent.replace("'", "\""));
        return configurationFileUnderTest;
    }
}
