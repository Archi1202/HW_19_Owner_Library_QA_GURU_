package tests;

import api.BooksActions;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static data.AuthData.USER_NAME;

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

        profilePage
                .openPage()
                .removeBanners()
                .checkLoginData(USER_NAME)
                .checkBookInUserProfile(isbn)
                .deleteBookFromProfile(isbn)
                .checkBookAfterDeletion(isbn);

        booksApi.checkResultOnApi();
    }
}
