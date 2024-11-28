package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

    private final SelenideElement
            userNameValue = $("#userName-value"),
            firstBookRow = $$(".rt-tr-group").first(),
            deleteConfirmButton = $("#closeSmallModal-ok"),
            notLoggedInBanner = $(".form-label");

    private static final String BOOK_LINK_TEMPLATE = "a[href='/profile?book=%s']";
    private static final String DELETE_BUTTON_SELECTOR = "#delete-record-undefined";

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

    @Step("Verify that a book is available in the user profile")
    public ProfilePage checkBookInUserProfile(String isbn) {
        firstBookRow.$(String.format(BOOK_LINK_TEMPLATE, isbn)).shouldBe(visible);
        return this;
    }

    @Step("Delete a book from the profile page")
    public ProfilePage deleteBookFromProfile() {
        firstBookRow.$(DELETE_BUTTON_SELECTOR).shouldBe(visible).click();
        deleteConfirmButton.shouldBe(visible).click();
        return this;
    }

    @Step("Verify that the book is not present in the profile after deletion")
    public ProfilePage checkBookAfterDeletion(String isbn) {
        firstBookRow.$(String.format(BOOK_LINK_TEMPLATE, isbn)).shouldNot(exist);
        return this;
    }
}
