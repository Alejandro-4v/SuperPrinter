package org.superprinter.office;

import org.superprinter.library.Librarian;
import org.superprinter.server_room.Server;
import org.superprinter.server_room.SuperTransformer;
import org.superprinter.utils.DocumentType;
import org.superprinter.utils.Finals;

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
        while (!Thread.currentThread().isInterrupted()) {
            sendDocument();
            takeABreak();
        }
    }

    private void takeABreak() {
        try {
            System.out.println("[EMPLOYEE] " + name + " is taking a short break...");
            Thread.sleep(Finals.EMPLOYEE_SLEEP_TIME_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sendDocument() {
        Document document = createNewDocument();
        System.out.println(
                "[EMPLOYEE] " + name + " is finished writing '" + document.title() + "' and sending it to the server.");
        String json = serialize(document);
        deliver(json);
    }

    private String serialize(Document document) {
        return SuperTransformer.transformDocumentIntoJson(document);
    }

    private void deliver(String json) {
        COMMON_SERVER.receiveDocument(json);
    }

    public Document createNewDocument() {
        String title = getNextDocumentFilename();
        String content = LIBRARIAN.askForRandomBook();
        DocumentType type = chooseRandomColorToPrint();
        return new Document(title, content, type, this.name);
    }

    private String getNextDocumentFilename() {
        int newDocumentIndex = WRITTEN_DOCUMENTS.incrementAndGet();
        return "document_" + this.name + "_" + newDocumentIndex + ".pdf";
    }

    protected static DocumentType chooseRandomColorToPrint() {
        int chosenColor = RND.nextInt();

        if (chosenColor % 2 == 0) {
            return DocumentType.BLACK;
        } else {
            return DocumentType.COLOR;
        }
    }

}
