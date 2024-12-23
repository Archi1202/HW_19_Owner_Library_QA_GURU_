package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import tests.TestBase;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage extends TestBase {

    private final SelenideElement
            userNameValue = $("#userName-value"),
            firstBookRow = $$(".rt-tr-group").first(),
            deleteConfirmButton = $("#closeSmallModal-ok"),
            notLoggedInBanner = $(".form-label");

    private final String bookLink = "a[href='/profile?book=%s']";
    private final String deleteButtonSelector = "#delete-record-undefined";

    @Step("Open the Profile Page")
    public ProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Verify that the correct user is logged in")
    public ProfilePage checkLoginData(String username) {
        userNameValue.shouldHave(text(username));
        return this;
    }

    @Step("Verify that non-authorized users see the banner")
    public ProfilePage checkNonAuthUserBanner() {
        notLoggedInBanner.shouldBe(visible)
                .shouldHave(text("Currently you are not logged into the Book Store application, please visit the "));
        return this;
    }

    @Step("Verify that new book is available in the user profile from first row")
    public ProfilePage checkBookInUserProfile(String isbn) {
        firstBookRow.$(String.format(bookLink, isbn)).shouldBe(visible);
        return this;
    }

    @Step("Delete first book from the profile page")
    public ProfilePage deleteBookFromProfile(String isbn) {
        firstBookRow.$(deleteButtonSelector).shouldBe(visible).click();
        deleteConfirmButton.shouldBe(visible).click();
        return this;
    }

    @Step("Verify that the book is not present in the profile after deletion")
    public ProfilePage checkBookAfterDeletion(String isbn) {
        firstBookRow.$(String.format(bookLink, isbn)).shouldNot(exist);
        return this;
    }
}
