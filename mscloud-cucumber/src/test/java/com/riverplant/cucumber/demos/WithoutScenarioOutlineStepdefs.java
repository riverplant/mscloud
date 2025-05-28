package com.riverplant.cucumber.demos;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WithoutScenarioOutlineStepdefs
{
    private boolean login;

    @Given("open the login page")
    public void openLoginPage() {

        System.out.println("open the login page");
    }

    @When("user {string} with password {string}")
    public void login(String username, String password)
    {
        this.login = ("admin".equals(username) && "123456".equals(password));
    }

    @Then("the user login failed")
    public void theUserLoginFailed()
    {
        assertThat(this.login, equalTo(false));
    }

    @Then("the user login success")
    public void theUserLoginSuccess()
    {
        assertThat(this.login, equalTo(true));
    }
}
