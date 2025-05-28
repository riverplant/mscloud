package com.riverplant.cucumber.demos;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class MultipleWithoutAndBut {

    @Given("^user open the url of git$")
    public void openGitUrl() {
        System.out.println("打开 Git 地址");
    }

    @Given("^user open the url of jenkins$")
    public void openJenkinsUrl() {
        System.out.println("打开 Jenkins 地址");
    }

    @When("^click build job link$")
    public void clickBuildJob() {
        System.out.println("点击构建任务链接");
    }

    @Then("^the job will be started$")
    public void jobWillStart() {
        System.out.println("任务开始执行");
    }

    @When("^click the job id link$")
    public void clickJobId() {
        System.out.println("点击任务 ID");
    }

    @When("^choice the job console$")
    public void chooseJobConsole() {
        System.out.println("选择控制台");
    }

    @When("^goto the console page$")
    public void goToConsolePage() {
        System.out.println("进入控制台页面");
    }

    @Then("^the job building log will be print$")
    public void printJobLog() {
        System.out.println("打印构建日志");
    }

    @Then("^the last job can see successfully state$")
    public void lastJobSuccessState() {
        System.out.println("最后一个任务显示成功状态");
    }
}
