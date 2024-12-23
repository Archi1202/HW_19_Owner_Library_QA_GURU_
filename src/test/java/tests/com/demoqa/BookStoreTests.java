package tests.com.demoqa;

import api.BooksActions;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProfilePage;
import tests.TestBase;

import static data.AuthData.USER_NAME;
import static data.AuthData.USER_PASSWORD;

@Tag("API")
@DisplayName("Verification of DemoQA Platform Book Store")

public class BookStoreTests extends TestBase {
    @DisplayName("Remove the book from UI side")
    @Test
    @WithLogin
    void SuccessfulDeletionOfBookTest() {

        BooksActions booksApi = new BooksActions();
        booksApi.deleteAllBooks();

        String isbn = booksApi.getRandomIsbn();
        booksApi.addNewBook(isbn);

        ProfilePage profilePage = new ProfilePage();

        LoginPage loginPage = new LoginPage();

        loginPage
                .openPage()
                .LoginWithValidData(USER_NAME, USER_PASSWORD);

        profilePage
                .openPage()
                .checkLoginData(USER_NAME)
                .checkBookInUserProfile(isbn)
                .deleteBookFromProfile(isbn)
                .checkBookAfterDeletion(isbn);

        booksApi.checkResultOnApi();
    }
}
