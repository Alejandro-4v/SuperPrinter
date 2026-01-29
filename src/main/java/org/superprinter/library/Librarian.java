package org.superprinter.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jdk.management.jfr.FlightRecorderMXBean;
import org.superprinter.utils.Finals;

public class Librarian {

    private List<String> books;
    private Random rnd = new Random();
    
    private String name;
    private String workplace;

    public static void main(String[] args) {
        Librarian l = new Librarian("Jarol", Finals.LIBRARY_BOOKS_PATH);

        System.out.println(l.getRandomBook());
    }

    public Librarian(String name, String workplace) {
        Random rnd = new Random();

        this.name = name;
        this.workplace = workplace;
        filterBooksOnLibrary();
    }
    
    public String getRandomBook() {
        if (books == null || books.isEmpty()) {
            return null;
        }
        String bookName = selectRandomBook();
        Path bookPath = Path.of(this.workplace, bookName);
        try {
            return Files.readString(bookPath);
        } catch (IOException e) {
            return null;
        }
    }

    private String selectRandomBook() {

        int librarySize = books.size();
        int randomIndex = this.rnd.nextInt(librarySize);
        
        return books.get(randomIndex);

    }

    private List<String> listCurrentDirectory() throws IOException {
        Path currentPath = Path.of(this.workplace);

        try (Stream<Path> stream = Files.list(currentPath)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    private void filterBooksOnLibrary() {
        try {
            List<String> unfilteredBooks = listCurrentDirectory();
            if (unfilteredBooks == null) {
                books = new ArrayList<String>();
            } else {
                books = unfilteredBooks;
                books.remove("Library.java");
            }
        } catch (IOException e) {
            books = new ArrayList<String>();
        }
    }
}
