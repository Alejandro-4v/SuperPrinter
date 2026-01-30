package org.superprinter.office;

import org.superprinter.library.Librarian;
import org.superprinter.server_room.Server;
import org.superprinter.utils.DocumentType;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Employee implements Runnable {

    private String name;
    private int threadId;

    private final AtomicInteger WRITTEN_DOCUMENTS = new AtomicInteger();

    private static final Random RND = new Random();

    private final Server COMMON_SERVER;
    private final Librarian LIBRARIAN;

    public Employee(String name, Server COMMON_SERVER, Librarian LIBRARIAN) {
        this.name = name;
        this.COMMON_SERVER = COMMON_SERVER;
        this.LIBRARIAN = LIBRARIAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public void run() {

    };


}
