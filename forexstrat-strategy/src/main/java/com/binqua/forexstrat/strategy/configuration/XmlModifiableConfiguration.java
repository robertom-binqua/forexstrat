package com.binqua.forexstrat.strategy.configuration;

import com.binqua.forexstrat.feedreader.core.model.CurrencyPair;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class XmlModifiableConfiguration implements ModifiableConfiguration {

    private String strategyStartValue;
    private boolean isASellStrategy;
    private String pipsToBePaidToTheBroker;
    private SelectionConfiguration retracedToValueSelectionConfiguration;
    private SelectionConfiguration marketWorstCaseValueSelectionConfiguration;
    private List<StrategySingleEntryConfiguration> entries = new ArrayList<StrategySingleEntryConfiguration>();
    private String costOfTheSecondCurrencyInPounds;
    private CurrencyPair currencyPair;
    private ReportConfiguration reportConfiguration;

    public String asString() {
        Writer stringWriter = new StringWriter();
        XMLStreamWriter xmlStreamWriter = createAndAttachXmlStreamWriterTo(stringWriter);
        addTradingConfigurationRootElement(xmlStreamWriter);
        return prettyFormat(stringWriter.toString());
    }

    public void addStrategyStartValue(String value) {
        strategyStartValue = value;
    }

    public void addIsASellStrategy(boolean isASellStrategy) {
        this.isASellStrategy = isASellStrategy;
    }

    public void addPipsToBePaidToTheBroker(String pipsToBePaidToTheBroker) {
        this.pipsToBePaidToTheBroker = pipsToBePaidToTheBroker;
    }

    public void addRetracedToValue(SelectionConfiguration selectionConfiguration) {
        this.retracedToValueSelectionConfiguration = selectionConfiguration;
    }

    public void addMarketWorstCaseValueConfiguration(SelectionConfiguration selectionConfiguration) {
        this.marketWorstCaseValueSelectionConfiguration = selectionConfiguration;
    }

    public void addEntry(StrategySingleEntryConfiguration strategyConfigurationEntry) {
        entries.add(strategyConfigurationEntry);
    }

    public void addCostOfTheSecondCurrencyInPounds(String costOfTheCurrencyInPounds) {
        this.costOfTheSecondCurrencyInPounds = costOfTheCurrencyInPounds;
    }

    public void addCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    private void addTradingConfigurationRootElement(XMLStreamWriter xmlStreamWriter) {
        String rootElement = "trading-configuration";
        try {
            xmlStreamWriter.writeStartElement(rootElement);
            addStrategyStartValueElement(xmlStreamWriter, strategyStartValue);
            addIsASellStrategyElement(xmlStreamWriter, isASellStrategy);
            addPipsToBePaidToTheBrokerElement(xmlStreamWriter, pipsToBePaidToTheBroker);
            addRetracedToElement(xmlStreamWriter, retracedToValueSelectionConfiguration);
            addMarketWorstCaseValueElement(xmlStreamWriter, marketWorstCaseValueSelectionConfiguration);
            addCostOfTheSecondCurrencyInPoundsElement(xmlStreamWriter, costOfTheSecondCurrencyInPounds);
            addCurrencyPairElement(xmlStreamWriter, currencyPair);
            addStrategyEntriesElement(xmlStreamWriter, entries);
            addReportConfigurationElement(xmlStreamWriter, reportConfiguration);
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Problem adding element " + rootElement, e);
        }
    }

    private void addReportConfigurationElement(XMLStreamWriter xmlStreamWriter, ReportConfiguration reportConfiguration) {
        try {
            xmlStreamWriter.writeStartElement("report-configuration");
            addElementWithValue(xmlStreamWriter, "maximum-market-value", reportConfiguration.maxMarketValue());
            addElementWithValue(xmlStreamWriter, "minimum-market-value", reportConfiguration.minMarketValue());
            addElementWithValue(xmlStreamWriter, "resolution-in-pips", String.valueOf(reportConfiguration.resolutionInPips().incrementInPips()));
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Problem adding report configuration element", e);
        }
    }

    private void addCostOfTheSecondCurrencyInPoundsElement(XMLStreamWriter xmlStreamWriter, String costOfTheSecondCurrencyInPounds) {
        addElementWithValue(xmlStreamWriter, "cost-of-the-currency-in-pounds", costOfTheSecondCurrencyInPounds);
    }

    private void addStrategyEntriesElement(XMLStreamWriter xmlStreamWriter, List<StrategySingleEntryConfiguration> entries) {
        try {
            xmlStreamWriter.writeStartElement("strategy-configuration");
            for (StrategySingleEntryConfiguration strategyConfigurationEntry : entries) {
                addEntry(strategyConfigurationEntry, xmlStreamWriter);
            }
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Problem adding entries element", e);
        }
    }

    private void addEntry(StrategySingleEntryConfiguration strategyConfigurationEntry, XMLStreamWriter xmlStreamWriter) {
        try {
            xmlStreamWriter.writeStartElement("strategy-configuration-entry");
            xmlStreamWriter.writeAttribute("index", strategyConfigurationEntry.getSliderIndex());
            xmlStreamWriter.writeAttribute("number-of-contracts", String.valueOf(strategyConfigurationEntry.getNumberOfContracts()));
            xmlStreamWriter.writeAttribute("market-value", strategyConfigurationEntry.getValue());
            xmlStreamWriter.writeAttribute("selection", strategyConfigurationEntry.getSelection());
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Problem adding element strategy-configuration-entry " + strategyConfigurationEntry, e);
        }
    }

    private void addMarketWorstCaseValueElement(XMLStreamWriter xmlStreamWriter, SelectionConfiguration marketWorstCaseValueSelectionConfiguration) {
        addElementWithValue(xmlStreamWriter, "market-worst-case-value", marketWorstCaseValueSelectionConfiguration);
    }

    private void addCurrencyPairElement(XMLStreamWriter xmlStreamWriter, CurrencyPair currencyPair) {
        addElementWithValue(xmlStreamWriter, "currency-pair", currencyPair.asString());
    }

    private void addRetracedToElement(XMLStreamWriter xmlStreamWriter, SelectionConfiguration retracedToValueSelectionConfiguration) {
        addElementWithValue(xmlStreamWriter, "retraced-to", retracedToValueSelectionConfiguration);
    }

    private void addPipsToBePaidToTheBrokerElement(XMLStreamWriter xmlStreamWriter, String pipsToBePaidToTheBroker) {
        addElementWithValue(xmlStreamWriter, "pips-to-be-paid-to-the-broker", pipsToBePaidToTheBroker);
    }

    private XMLStreamWriter createAndAttachXmlStreamWriterTo(Writer stringWriter) {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        try {
            return xmlOutputFactory.createXMLStreamWriter(stringWriter);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Problem creating xml writer", e);
        }
    }

    private String prettyFormat(String input) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addIsASellStrategyElement(XMLStreamWriter xmlStreamWriter, boolean aSellStrategy) {
        addElementWithValue(xmlStreamWriter, "is-a-sell-strategy", String.valueOf(aSellStrategy));
    }

    private void addStrategyStartValueElement(XMLStreamWriter xmlStreamWriter, String strategyStartValue) {
        addElementWithValue(xmlStreamWriter, "strategy-start-value", strategyStartValue);
    }

    private void addElementWithValue(XMLStreamWriter xmlStreamWriter, String elementName, String value) {
        try {
            xmlStreamWriter.writeStartElement(elementName);
            xmlStreamWriter.writeAttribute("value", (value == null) ? "" : value);
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Problem adding element " + elementName + " with value " + value, e);
        }
    }

    private void addElementWithValue(XMLStreamWriter xmlStreamWriter, String elementName, SelectionConfiguration selectionConfiguration) {
        try {
            xmlStreamWriter.writeStartElement(elementName);
            xmlStreamWriter.writeAttribute("value", (selectionConfiguration.getMarketValue() == null) ? "" : selectionConfiguration.getMarketValue());
            xmlStreamWriter.writeAttribute("selection", (selectionConfiguration.getSelection() == null) ? "" : selectionConfiguration.getSelection());
            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Problem adding element " + elementName + " with selectionConfiguration " + selectionConfiguration, e);
        }
    }


    public void addReportConfiguration(ReportConfiguration reportConfiguration) {
        this.reportConfiguration = reportConfiguration;
    }
}
