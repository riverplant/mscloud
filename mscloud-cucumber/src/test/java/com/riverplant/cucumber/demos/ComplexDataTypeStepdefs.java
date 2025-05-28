package com.riverplant.cucumber.demos;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;

public class ComplexDataTypeStepdefs {
    @Given("^the user account information$")
    public void testUserAccountInformation() {

    }

    @Then("we can found user {string} with password {string}, phone {string} exists")
    public void weCanFoundUserWithPasswordPhoneExists(String name, String password, String phone) {
        System.out.println(name);
        System.out.println(password);
        System.out.println(phone);
        System.out.println("==============================================");
    }

    @Then("we verify following user exists")
    public void weVerifyFollowingUserExists(DataTable dataTable) {
        List<Map<String, String>> users = dataTable.asMaps(String.class, String.class);
        List<List<String>> lists = dataTable.asLists(String.class);
        lists.forEach(System.out::println);

        for ( Map<String, String> user :users)
        {
            System.out.println("User: " + user.get("name") + ", password: "+ user.get("password") + ", phone: " + user.get("phone"));
        }
    }
}
