package com.riverplant.cucumber.demos;

import io.cucumber.java.en.*;

public class MultipleWithAndBut {

    @Given("^jie open the url of git$")
    public void jie_open_the_url_of_git() {
        System.out.println("jie open the url of git");
    }

    @And("^jie open the url of jenkins$")
    public void jie_open_the_url_of_jenkins() {
        System.out.println("jie open the url of jenkins");
    }

    @When("^jie click build job link$")
    public void jie_click_build_job_link() {
        System.out.println("jie click build job link");
    }

    @Then("^jie the job will be started$")
    public void jie_the_job_will_be_started() {
        System.out.println("jie the job will be started");
    }

    @When("^jie click the job id link$")
    public void jie_click_the_job_id_link() {
        System.out.println("jie click the job id link");
    }

    @And("^jie choice the job console$")
    public void jie_choice_the_job_console() {
        System.out.println("jie choice the job console");
    }

    @And("^jie goto the console page$")
    public void jie_goto_the_console_page() {
        System.out.println("jie goto the console page");
    }

    @Then("^jie the job building log will be print$")
    public void jie_the_job_building_log_will_be_print() {
        System.out.println("jie the job building log will be print");
    }

    @And("^jie the last job can see successfully state$")
    public void jie_the_last_job_can_see_successfully_state() {
        System.out.println("jie the last job can see successfully state");
    }

    @But("^jie the job will not running at current$")
    public void jieTheJobWillNotRunningAtCurrent() {
        System.out.println("jie the job will not running at current");
    }
}
