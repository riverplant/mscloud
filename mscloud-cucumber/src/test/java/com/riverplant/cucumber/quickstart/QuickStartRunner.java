package com.riverplant.cucumber.quickstart;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//Junit4
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:com/riverplant/cucumber/features",   // ✅ 指定正确 feature 路径
        glue = "com.riverplant.cucumber.quickstart",              // 步骤定义包
        plugin = {"pretty","json:target/cucumber-report.json"}
)
public class QuickStartRunner
{
}
