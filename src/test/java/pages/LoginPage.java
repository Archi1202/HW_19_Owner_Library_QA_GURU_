package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private final SelenideElement
            userNameInput = $("#userName"),
            passwordInput = $("#password"),
            loginButtonSelector = $("#login");

    @Step("Open the Login Page")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Login with existing data")
    public LoginPage LoginWithValidData(String userName,String password){
        userNameInput.setValue(userName);
        passwordInput.setValue(password);
        loginButtonSelector.click();
        return this;
    }

}
