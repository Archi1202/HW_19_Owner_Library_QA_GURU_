package api;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.*;

import java.util.List;
import java.util.Random;

import static data.AuthData.USER_ID;
import static data.AuthData.USER_TOKEN;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static specs.Specification.*;

public class BooksActions {

    private RequestSpecification withAuth() {
        return given(requestSpecification)
                .header("Authorization", "Bearer " + USER_TOKEN);
    }


    @Step("Remove all books from the profile")
    public BooksActions deleteAllBooks() {
        given(requestSpecification)
                .header("Authorization", "Bearer " + USER_TOKEN)
                .queryParam("UserId", USER_ID)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec204);
        return this;
    }

    @Step("Add new book into the cart")
    public BooksActions addNewBook(String isbn){
        IsbnModel book = new IsbnModel();
        book.setIsbn(isbn);

        BooksCollectionRequestModel booksCollection = new BooksCollectionRequestModel();
        booksCollection.setUserId(USER_ID);
        booksCollection.setCollectionOfIsbns(List.of(book));
        given(requestSpecification)
                .header("Authorization", "Bearer " + USER_TOKEN)
                .body(booksCollection)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec201);
                return this;
    }

    @Step("Get a list of all books for user")
    public static List<BookModel> getUserBooks() {

        BooksFromProfileResponseModel response =
                given(requestSpecification)
                        .when()
                        .header("Authorization", "Bearer " + USER_TOKEN)
                        .get("/Account/v1/User/" + USER_ID)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(BooksFromProfileResponseModel.class);

        return response.getBooks();
    }


    @Step("Get list of all available books from shop")
    public BooksFromStoreResponseModel getAllBooks() {

        return given(requestSpecification)
                .when()
                .header("Authorization", "Bearer " + USER_TOKEN)
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec200)
                .extract().as(BooksFromStoreResponseModel.class);
    }

    @Step("Get random ISBN.")
    public String getRandomIsbn() {
        BooksFromStoreResponseModel booksFromStore = getAllBooks();
        if (booksFromStore == null || booksFromStore.getBooks().isEmpty()) {
            throw new IllegalStateException("No books available in the store to fetch a random ISBN.");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(booksFromStore.getBooks().size());
        return booksFromStore.getBooks().get(randomIndex).getIsbn();
    }


    @Step("Verify that cart is empty")
    public void checkResultOnApi() {

        BooksActions booksApi = new BooksActions();
        List<BookModel> books = BooksActions.getUserBooks();
        assertTrue(books.isEmpty(), "Expected the user's cart to be empty, but found: " + books);

    }
}
