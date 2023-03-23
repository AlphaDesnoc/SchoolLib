package fr.desnoc.gestionnary.objects;

import java.util.List;

public class Book {

    private final String isbn;
    private final String name;
    private final String published;
    private final String publisher;
    private final List<String> authors;
    private int numberOfBook;
    private String available;
    private int empruntedBooks;
    private short shelf;
    private String category;

    public Book(String isbn, String name, String published, String publisher, List<String> authors) {
        this.isbn = isbn;
        this.name = name;
        this.published = published;
        this.publisher = publisher;
        this.authors = authors;
        this.numberOfBook = 1;
        this.available = "Disponible";
        this.empruntedBooks = 0;
        this.shelf = 0;
        this.category = "Inconnue";
    }
    public Book(String isbn, String name, String published, String publisher, List<String> authors, int numberOfBook, int empruntedBooks, short shelf, String category) {
        this.isbn = isbn;
        this.name = name;
        this.published = published;
        this.publisher = publisher;
        this.authors = authors;
        this.numberOfBook = numberOfBook;
        this.available = "Disponible";
        this.empruntedBooks = empruntedBooks;
        this.shelf = shelf;
        this.category = category;
    }


    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getPublished() {
        return published;
    }

    public String getPublisher() {
        return publisher;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setNumberOfBook(int numberOfBook) {
        this.numberOfBook = numberOfBook;
    }

    public int getNumberOfBook() {
        return numberOfBook;
    }

    public void setEmpruntedBooks(int empruntedBooks) {
        this.empruntedBooks = empruntedBooks;
    }

    public void addEmpruntedBook(int emprunted){
        setEmpruntedBooks(getEmpruntedBooks() + emprunted);
    }

    public void addReturnBook(int returned){
        setEmpruntedBooks(getEmpruntedBooks() - returned);
    }
    public int getEmpruntedBooks() {
        return empruntedBooks;
    }

    public void setShelf(short shelf) {
        this.shelf = shelf;
    }

    public short getShelf() {
        return shelf;
    }

    public void setAvailable(boolean available) {
        if(!available){
            setAvailable("Indisponible");
        }else{
            setAvailable("Disponible");
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
