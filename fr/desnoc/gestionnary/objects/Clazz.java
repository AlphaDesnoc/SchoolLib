package fr.desnoc.gestionnary.objects;

import java.util.ArrayList;
import java.util.List;

public class Clazz {

    private final byte classNumber;
    private Teacher teacher;

    private final List<Book> empruntedBooks;

    public Clazz(byte classNumber, Teacher teacher){
        this.classNumber = classNumber;
        this.teacher = teacher;
        this.empruntedBooks = new ArrayList<>();
    }

    public byte getClassNumber() {
        return classNumber;
    }

    public String getClassName(){
        return "Classe " + classNumber;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Book> getEmpruntedBooks() {
        return empruntedBooks;
    }

    public void addBook(Book book){
        getEmpruntedBooks().add(book);
    }

    public void removeBook(Book book){
        for(int i = 0; i < getEmpruntedBooks().size(); i++){
            if(getEmpruntedBooks().get(i).getIsbn().equals(book.getIsbn())){
                getEmpruntedBooks().remove(i);
                break;
            }
        }
    }
}