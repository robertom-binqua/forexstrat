package com.binqua.forexstrat.feedreader.core.client;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PropertyFileConfigurationTest {

    private static final String FEED_READER_CONFIGURATION_SYSTEM_PROPERTY_KEY = "feedReader.properties";

    @After
    public void tearDown() {
        System.clearProperty(FEED_READER_CONFIGURATION_SYSTEM_PROPERTY_KEY);
    }

    private File propertiesFileAbsoluteLocation() throws IOException {
        final File configurationFile = File.createTempFile("myProperties", ".properties");
        configurationFile.deleteOnExit();
        return configurationFile;
    }

    @Test
    public void propertyAreReadFromTheFileSpecifiedViaSystemProperty() throws IOException {
        final File configurationFile = propertiesFileAbsoluteLocation();

        System.setProperty(FEED_READER_CONFIGURATION_SYSTEM_PROPERTY_KEY, configurationFile.getAbsolutePath());

        createAPropertyFileWithLines(configurationFile, "feedReaderHasToRun=true",
                "serverUrl=123",
                "user=x",
                "password=y",
                "numberOfSecondsBeforeRetry=3");

        final PropertyFileConfiguration propertyFileConfiguration = new PropertyFileConfiguration();

        assertThat("feedReaderHasToRun", propertyFileConfiguration.feedReaderHasToRun(), is(true));
        assertThat("serverUrl", propertyFileConfiguration.serverUrl(), is("123"));
        assertThat("user", propertyFileConfiguration.user(), is("x"));
        assertThat("password", propertyFileConfiguration.password(), is("y"));
        assertThat("numberOfSecondsBeforeRetry", propertyFileConfiguration.numberOfSecondsBeforeRetry(), is(3));
    }

    @Test
    public void givenThatConfigurationFileDoesNotExistThanPropertyFileConfigurationConstructorThrowsIllegalStateException() throws IOException {
        final String nonExistingFileName = "bla bla bla";
        System.setProperty(FEED_READER_CONFIGURATION_SYSTEM_PROPERTY_KEY, nonExistingFileName);

        try {
            new PropertyFileConfiguration();
            fail(IllegalStateException.class + " should have been thrown");
        } catch (IllegalStateException ise) {
            assertThat(ise.getMessage(), is(equalTo("Problem reading configuration file " + nonExistingFileName)));
        }

    }

    @Test
    public void givenThatSystemPropertyForConfigurationFileIsNotSpecifiedThanAnyPropertyThrowsIllegalStateException() throws IOException {
        try {
            new PropertyFileConfiguration();
            fail(IllegalStateException.class + " should have been thrown");
        } catch (IllegalStateException ise) {
            assertThat(ise.getMessage(), is(equalTo("System property " + FEED_READER_CONFIGURATION_SYSTEM_PROPERTY_KEY + " has to be specified")));
        }
    }

    private void createAPropertyFileWithLines(File configurationFile, String... lines) throws IOException {
        FileUtils.writeLines(configurationFile, Arrays.asList(lines));
    }

}
