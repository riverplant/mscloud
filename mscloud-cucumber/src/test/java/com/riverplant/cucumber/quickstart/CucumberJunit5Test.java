package com.riverplant.cucumber.quickstart;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/riverplant/cucumber/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.riverplant.cucumber.quickstart")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,json:target/cucumber-report.json")
public class CucumberJunit5Test {

}
