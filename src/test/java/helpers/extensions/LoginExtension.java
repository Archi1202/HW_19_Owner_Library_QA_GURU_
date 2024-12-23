package helpers.extensions;

import api.Authorization;
import models.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static data.AuthData.*;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {

        USER_NAME = System.getProperty("storeUserName", "login");
        USER_PASSWORD = System.getProperty("storeUserPassword", "password");

        LoginResponseModel authResponse = Authorization.getAuthData(USER_NAME, USER_PASSWORD);

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));

        USER_ID = authResponse.getUserId();
        USER_TOKEN = authResponse.getToken();
        EXPIRES = authResponse.getExpires();
        CREATE_DATE = authResponse.getCreatedDate();
        IS_ACTIVE = authResponse.getIsActive();
    }
}