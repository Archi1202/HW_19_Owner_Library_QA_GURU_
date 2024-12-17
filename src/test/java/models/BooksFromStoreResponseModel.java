package models;

import lombok.Data;

import java.util.List;

@Data
public class BooksFromStoreResponseModel {

    private List<BookModel> books;
}