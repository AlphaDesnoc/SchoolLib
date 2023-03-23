package fr.desnoc.gestionnary.managers;

import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FileManager {

    public FileHandler getLoggerFile() throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
        String dateS = format.format(new Date());
        FileHandler handler = new FileHandler(getLoggerDir() + File.separator + "logs_" + dateS + ".log");
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat logTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(record.getMillis());
                return "["+ record.getLevel() + "]" + " "
                        + logTime.format(cal.getTime())
                        + " || "
                        + record.getSourceClassName().substring(
                        record.getSourceClassName().lastIndexOf(".")+1,
                        record.getSourceClassName().length())
                        + "."
                        + record.getSourceMethodName()
                        + "() : "
                        + record.getMessage() + "\n";
            }
        });
        return handler;
    }

    public Path getBooksFile() throws IOException {

        Path path = Paths.get(getDir() + File.separator + "books.json");

        if(!Files.isDirectory(path)){
            if(!Files.exists(path)){
                Files.createFile(path);
            }
        }

        return path;
    }

    public Path getClassesFile() throws IOException {

        Path path = Paths.get(getDir() + File.separator + "classes.json");

        if(!Files.isDirectory(path)){
            if(!Files.exists(path)){
                Files.createFile(path);
            }
        }

        return path;
    }

    public ImageView getImage(String imageName) throws IOException {
        Path path = Paths.get(getImageDir() + File.separator + imageName+ ".png");
        ImageView imageView = new ImageView(path.toString());
        return imageView;
    }

    private Path getLoggerDir() throws IOException {
        Path path = Paths.get(getDir() + File.separator + "logs");

        if(!Files.exists(path)){
            Files.createDirectories(path);
        }

        return path;
    }

    private Path getImageDir() throws IOException {
        Path path = Paths.get(getDir() + File.separator + "images");

        if(!Files.exists(path)){
            Files.createDirectories(path);
        }

        return path;
    }

    private Path getDir() throws IOException {

        String os = System.getProperty("os.name");
        Path path;

        if(os.toLowerCase().contains("win")){
            path = Paths.get(System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming" + File.separator + ".gestionnary");
        }else{
            path = Paths.get(System.getProperty("user.home") + File.separator + ".gestionnary");
        }

        if(!Files.exists(path)){
            Files.createDirectories(path);
        }

        return path;
    }

}
