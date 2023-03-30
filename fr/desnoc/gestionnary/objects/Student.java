package fr.desnoc.gestionnary.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student {

    private String name;
    private byte classNumber;

    private final List<Book> empruntedBooks;
    private String empruntDate;

    public Student(String name, byte className) {
        this.name = name;
        this.classNumber = className;
        this.empruntedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(byte classNumber) {
        this.classNumber = classNumber;
    }

    public String getClassName(){
        return "Classe : " + getClassNumber();
    }

    public List<Book> getEmpruntedBooks() {
        return empruntedBooks;
    }

    public Book getEmpruntedBook(){
        return getEmpruntedBooks().get(0);
    }

    public void setEmpruntDate(String empruntDate) {
        this.empruntDate = empruntDate;
    }

    public String getEmpruntDate() {
        return empruntDate;
    }

    public void addBook(Book book){
        System.out.println("Ajout");
        getEmpruntedBooks().add(book);
        System.out.println(getEmpruntedBooks().size());
        setEmpruntDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }

    public void removeBook(){
        getEmpruntedBooks().remove(0);
        setEmpruntDate(null);
    }
}
