package fr.desnoc.gestionnary.managers;

import com.google.gson.Gson;
import fr.desnoc.gestionnary.Main;
import fr.desnoc.gestionnary.objects.Clazz;
import fr.desnoc.gestionnary.objects.Teacher;
import fr.desnoc.gestionnary.objects.Book;
import fr.desnoc.gestionnary.objects.json.JsonBookModel;
import fr.desnoc.gestionnary.objects.json.VolumeInfo;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager {

    private final FileManager fileManager;
    private Book[] bookList;
    private Clazz[] classesList;
    private ArrayList<Book> books;
    private List<Clazz> classes;
    private final List<Teacher> teachers;
    private final Gson gson;

    private PanelManager panelManager;

    private final Logger logger = Logger.getLogger("Gestionnaire");


    public Manager() throws IOException {
        fileManager = new FileManager();
        books = new ArrayList<>();
        classes = new ArrayList<>();
        teachers = new ArrayList<>();
        gson = Main.getGson();
        fileManager.getBooksFile();
        readBooks();
        readClasses();
        logger.addHandler(fileManager.getLoggerFile());
        logger.setUseParentHandlers(false);
    }


    public void init(Stage stage){
        this.panelManager = new PanelManager(this, stage);
        this.panelManager.init();
        this.panelManager.showPanel(this.getPanelManager().getHomePanel());
    }


    public void saveBooks() throws IOException {
        File file = fileManager.getBooksFile().toFile();
        Writer writer = new FileWriter(file, StandardCharsets.UTF_8, false);
        gson.toJson(getBooks(), writer);
        writer.flush();
        panelManager.getManager().log("Enregistrement des livres dans le fichier books.json");
        writer.close();
    }

    public void saveClasses() throws IOException {
        File file = fileManager.getClassesFile().toFile();
        Writer writer = new FileWriter(file, StandardCharsets.UTF_8, false);
        gson.toJson(getClasses(), writer);
        writer.flush();
        panelManager.getManager().log("Enregistrement des classes dans le fichier classes.json");
        writer.close();
    }

    public void readBooks() throws IOException {
        String jsonString = Files.readString(fileManager.getBooksFile(), StandardCharsets.UTF_8);

        if(jsonString != null && !jsonString.equalsIgnoreCase("")){
            this.bookList = Main.getGson().fromJson(jsonString, Book[].class);
            ArrayList<Book> aBookList = new ArrayList<>();
            for(Book book : this.bookList){
                aBookList.add(book);
            }
            setBooks(aBookList);
        }
    }

    public void readClasses() throws IOException {
        String jsonString = Files.readString(fileManager.getClassesFile(), StandardCharsets.UTF_8);

        if(jsonString != null && !jsonString.equalsIgnoreCase("")){
            this.classesList = Main.getGson().fromJson(jsonString, Clazz[].class);
            ArrayList<Clazz> aClazzList = new ArrayList<>();
            for(Clazz clazz : this.classesList){
                aClazzList.add(clazz);
            }
            setClasses(aClazzList);
        }
    }

    public Book getBook(String isbn){
        AtomicReference<Book> booK = null;
        getBooks().forEach(book -> {
            if(book.getIsbn().equals(isbn)){
                booK.set(book);
            }
        });
        return booK.get();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void setClasses(ArrayList<Clazz> classes) { this.classes = classes; }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book){
        getBooks().add(book);
    }

    public void addClasses(Clazz clazz) {
        getClasses().add(clazz);
        try {
            saveClasses();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeBook(String isbn){
        for(int i = 0; i < getBooks().size(); i++){
            if(getBooks().get(i).getIsbn().equals(isbn)){
                log("Retrait de livre effectué : " + getBooks().get(i).getName());
                getBooks().remove(i);
            }
        }
        try {
            saveBooks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeClass(byte nbClass){
        for (int i = 0; i < getClasses().size(); i++){
            if(getClasses().get(i).getClassNumber() == nbClass){
                log("Retrait de classe effectué : Classe " + getClasses().get(i).getClassNumber());
                getClasses().remove(i);
            }
        }
        try{
            saveClasses();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public boolean createBook(String isbn) throws IOException {
        if(searchBook(isbn) != null){
            Book book = searchBook(isbn);
            if(!getBooks().contains(book)){
                panelManager.showModifyBookTwo(book);
            }
            return true;
        }else{
            return false;
        }
    }

    public void createBook(String isbn, String name, String datePublish, String publisher, String authors) throws IOException {

        Book book = new Book(isbn, name, datePublish, publisher, Collections.singletonList(authors));

        getBooks().add(book);
        saveBooks();
    }

    private Book searchBook (String isbn) throws MalformedURLException {
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        URL url = new URL(urlString);
        StringBuilder builder = new StringBuilder();
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String str;
            while ((str = bufferedReader.readLine()) != null){
                builder.append(str);
            }

            String jsonString = builder.toString();
            JsonBookModel model = Main.getGson().fromJson(jsonString, JsonBookModel.class);

            if(model.items == null){
                return null;
            }

            VolumeInfo info = model.items.get(0).volumeInfo;

            Book book = new Book(isbn, info.title, info.publishedDate, info.publisher, info.authors);

            return book;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void empruntBook(Clazz clazz, Book book, int nb) throws IOException {
        if(nb > book.getNumberOfBook()){
            log("&");
            /**
             * TODO message d'erreur a afficher
             */
        }else {
            for(int i = 0; i < nb; i++){
                clazz.addBook(book);
            }
            if(nb == book.getNumberOfBook()){
                book.setAvailable(false);
            }
            book.setNumberOfBook(book.getNumberOfBook()-nb);
            book.addEmpruntedBook(nb);
        }
        saveBooks();
        saveClasses();
    }

    public void returnBook(Clazz clazz, Book book, int nb) throws IOException {
        if(nb > book.getEmpruntedBooks()){
            /**
             * TODO message d'erreur a afficher
             */
        }else {
            for(int i = 0; i < nb; i++){
                clazz.removeBook(book);
            }
            book.setAvailable(true);
            book.setNumberOfBook(book.getNumberOfBook() + nb);
            book.addReturnBook(nb);
        }
        saveBooks();
        saveClasses();
    }

    public void log(String message){
        logger.log(Level.INFO, message);
    }

    public void log(String message, Exception exception){
        logger.log(Level.INFO, message, exception);
    }

    public void warn(String message){
        logger.log(Level.WARNING, message);
    }

    public void warn(String message, Exception exception){
        logger.log(Level.WARNING, message, exception);
    }

    public void err(String message){
        logger.log(Level.SEVERE, message);
    }

    public void err(String message, Exception exception){
        logger.log(Level.SEVERE, message, exception);
    }

    public List<Clazz> getClasses() {
        return classes;
    }
    public void removeClass(Clazz clazz){
        getClasses().remove(clazz);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
    public void addTeacher(Teacher teacher){
        getTeachers().add(teacher);
    }
    public void removeTeacher(Teacher teacher){
        getTeachers().remove(teacher);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public PanelManager getPanelManager() {
        return panelManager;
    }

    public Logger getLogger() {
        return logger;
    }
}