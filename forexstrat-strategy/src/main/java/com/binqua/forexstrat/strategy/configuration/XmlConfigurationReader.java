package com.binqua.forexstrat.strategy.configuration;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;
import com.binqua.forexstrat.feedreader.core.model.CurrencyPairs;
import com.binqua.forexstrat.strategy.report.ReportResolution;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.binqua.forexstrat.strategy.configuration.ReportConfigurationBuilder.aReportConfiguration;

public class XmlConfigurationReader implements ConfigurationReader {
    private Document configurationDocumentElement;
    private String strategyStartingValue;
    private boolean isASellingStrategy;
    private String pipsToBePaidToTheBroker;
    private SelectionConfiguration selectionConfiguration;
    private SelectionConfiguration retraceToSelectionConfiguration;
    private StrategyConfiguration strategyConfiguration;
    private CurrencyPair currencyPair;
    private ReportConfiguration reportConfiguration;
    private CurrencyPairs currencyPairs;
    private String actualMarketValue;
    private String configurationContent;

    public XmlConfigurationReader(File configurationFile, CurrencyPairs currencyPairs) {
        configurationDocumentElement = extractDocumentElementFrom(configurationFile);
        try {
            this.configurationContent = FileUtils.readFileToString(configurationFile);
        } catch (IOException e) {
            throw new ConfigurationReaderException(e);
        }
        readConfiguration(currencyPairs);
    }

    public XmlConfigurationReader(String configurationContent, CurrencyPairs currencyPairs) {
        configurationDocumentElement = extractDocumentElementFrom(configurationContent);
        this.configurationContent = configurationContent;
        readConfiguration(currencyPairs);
    }

    private void readConfiguration(CurrencyPairs currencyPairs) {
        this.currencyPairs = currencyPairs;
        strategyStartingValue = readStrategyStartingValue();
        isASellingStrategy = readIsASellingStrategyValue();
        pipsToBePaidToTheBroker = readPipsToBePaidToTheBrokerValue();
        actualMarketValue = readActualMarketValue();
        retraceToSelectionConfiguration = readRetracedToConfiguration();
        selectionConfiguration = readMarketWorstCaseValueConfiguration();
        strategyConfiguration = readStrategyConfiguration();
        reportConfiguration = readReportConfiguration();
        currencyPair = readCurrencyPair();
    }

    private Document extractDocumentElementFrom(String configurationContent) {
        DocumentBuilder documentBuilder = createADocumentBuilder();
        Document parse = parse(configurationContent, documentBuilder);
        return parse.getDocumentElement().getOwnerDocument();
    }

    private Document extractDocumentElementFrom(File configurationFile) {
        DocumentBuilder documentBuilder = createADocumentBuilder();
        Document parse = parse(configurationFile, documentBuilder);
        return parse.getDocumentElement().getOwnerDocument();
    }

    private Document parse(String configurationContent, DocumentBuilder documentBuilder) {
        try {
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(configurationContent));
            return documentBuilder.parse(inputSource);
        } catch (SAXException e) {
            throw new ConfigurationReaderException(e);
        } catch (IOException e) {
            throw new ConfigurationReaderException(e);
        }
    }

    private Document parse(File configurationFile, DocumentBuilder documentBuilder) {
        try {
            return documentBuilder.parse(configurationFile);
        } catch (SAXException e) {
            throw new ConfigurationReaderException(e);
        } catch (IOException e) {
            throw new ConfigurationReaderException(e);
        }
    }

    private DocumentBuilder createADocumentBuilder() {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return documentBuilder;
    }

    private ReportConfiguration readReportConfiguration() {
        Document reportConfigurationDocument = configurationDocumentElement.getElementsByTagName("report-configuration").item(0).getOwnerDocument();

        return aReportConfiguration()
                .withMaxMarketValue(readValueAttributeFromElementAsString(reportConfigurationDocument, "maximum-market-value"))
                .withMinMarketValue(readValueAttributeFromElementAsString(reportConfigurationDocument, "minimum-market-value"))
                .withResolutionInPips(ReportResolution.resolutionFrom(readValueAttributeFromElementAsString(reportConfigurationDocument, "resolution-in-pips")))
                .build();
    }

    private String readValueAttributeFromElementAsString(Document reportConfigurationDocument, String elementName) {
        return readAttribute(reportConfigurationDocument.getElementsByTagName(elementName), "value");
    }

    private CurrencyPair readCurrencyPair() {
        return currencyPairs.currencyPairFrom(readTheFirstXmlAttributeFromElement("currency-pair"));
    }

    private String readActualMarketValue() {
        return readTheFirstXmlAttributeFromElement("actual-market-value");
    }

    private StrategySingleEntryConfiguration readStrategyConfiguration(Node configurationEntries) {
        int index = Integer.parseInt(readStrategyConfigurationIndex(configurationEntries));
        String numberOfContracts = readStrategyConfigurationNumberOfContracts(configurationEntries);
        SelectionConfiguration selectionConfiguration = readStrategyConfigurationSelection(configurationEntries);
        return new StrategySingleEntryConfiguration(index, Double.valueOf(numberOfContracts), selectionConfiguration);
    }

    private SelectionConfiguration readStrategyConfigurationSelection(Node configurationEntries) {
        return new SelectionConfiguration(readStrategyConfigurationMarketValue(configurationEntries), readStrategyConfigurationMarketValueSelection(configurationEntries));
    }

    private String readStrategyConfigurationMarketValueSelection(Node configurationEntries) {
        return readAttribute(configurationEntries, "market-value");
    }

    private String readStrategyConfigurationMarketValue(Node configurationEntries) {
        return readAttribute(configurationEntries, "selection");
    }

    private String readAttribute(Node configurationEntries, String selection) {
        return configurationEntries.getAttributes().getNamedItem(selection).getNodeValue();
    }

    private String readStrategyConfigurationNumberOfContracts(Node configurationEntries) {
        return readAttribute(configurationEntries, "number-of-contracts");
    }

    private String readStrategyConfigurationIndex(Node configurationEntries) {
        return configurationEntries.getAttributes().getNamedItem("index").getNodeValue();
    }

    private boolean readIsASellingStrategyValue() {
        return "true".equals(isASellingStrategyValue());
    }

    private SelectionConfiguration readMarketWorstCaseValueConfiguration() {
        return new SelectionConfiguration(getMarketWorstCaseValueSelection(), getMarketWorstCaseValue());
    }

    private String getMarketWorstCaseValue() {
        return marketWorstCaseValueElement().item(0).getAttributes().getNamedItem("value").getNodeValue();
    }

    private NodeList marketWorstCaseValueElement() {
        return configurationDocumentElement.getElementsByTagName("market-worst-case-value");
    }

    private String readStrategyStartingValue() {
        return readTheFirstXmlAttributeFromElement("strategy-start-value");
    }

    private String readTheFirstXmlAttributeFromElement(String elementName) {
        return configurationDocumentElement.getElementsByTagName(elementName).item(0).getAttributes().item(0).getNodeValue();
    }

    private String isASellingStrategyValue() {
        return readValueAttributeFromElementAsString(configurationDocumentElement, "is-a-sell-strategy");
    }

    private String readPipsToBePaidToTheBrokerValue() {
        return readTheFirstXmlAttributeFromElement("pips-to-be-paid-to-the-broker");
    }

    private SelectionConfiguration readRetracedToConfiguration() {
        return new SelectionConfiguration(getRetracedToSelection(), getRetracedToMarketValue());
    }

    private String getRetracedToMarketValue() {
        return readAttribute(getRetraceToElement(), "value");
    }

    private String getRetracedToSelection() {
        return readAttribute(getRetraceToElement(), "selection");
    }

    private NodeList getRetraceToElement() {
        return configurationDocumentElement.getElementsByTagName("retraced-to");
    }

    public String wholeStrategyEntryValue() {
        return strategyStartingValue;
    }

    public boolean isASellingStrategy() {
        return isASellingStrategy;
    }

    public String getPipsToBePaidToTheBroker() {
        return pipsToBePaidToTheBroker;
    }

    public StrategyConfiguration getStrategyConfiguration() {
        return strategyConfiguration;
    }

    public String getCostOfTheRightCurrencyInPounds() {
        return readValueAttributeFromElementAsString(configurationDocumentElement, "cost-of-the-currency-in-pounds");
    }

    public SelectionConfiguration getRetracedToConfiguration() {
        return retraceToSelectionConfiguration;
    }

    public SelectionConfiguration getMarketWorstValueConfiguration() {
        return selectionConfiguration;
    }

    public String getMarketWorstCaseValueSelection() {
        return readAttribute(marketWorstCaseValueElement(), "selection");
    }

    private String readAttribute(NodeList nodeList, String attributeName) {
        return nodeList.item(0).getAttributes().getNamedItem(attributeName).getNodeValue();
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public ReportConfiguration getReportConfiguration() {
        return reportConfiguration;
    }

    public String actualMarketValue() {
        return actualMarketValue;
    }

    @Override
    public String name() {
        return readTheFirstXmlAttributeFromElement("name");
    }

    private StrategyConfiguration readStrategyConfiguration() {
        NodeList childNodes = configurationDocumentElement.getElementsByTagName("strategy-configuration").item(0).getChildNodes();
        Node nodeValue = childNodes.item(0);

        Node strategyConfigurationNode = nodeValue.getNextSibling();

        List<StrategySingleEntryConfiguration> strategyConfigurationEntries = new ArrayList<StrategySingleEntryConfiguration>();

        while (strategyConfigurationNode != null) {
            strategyConfigurationEntries.add(readStrategyConfiguration(strategyConfigurationNode));
            strategyConfigurationNode = strategyConfigurationNode.getNextSibling().getNextSibling();
        }

        return new StrategyConfiguration(strategyConfigurationEntries);
    }

    public String asString() {
        return configurationContent;
    }
}
