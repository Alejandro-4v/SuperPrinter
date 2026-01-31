package org.superprinter.server_room;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.superprinter.office.Document;
import org.superprinter.stationers_room.Page;
import org.superprinter.stationers_room.SuperArchive;
import org.superprinter.utils.DocumentType;
import org.superprinter.utils.Finals;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Server {

    public void receiveDocument(String documentJson) {
        Document document = SuperTransformer.transformJsonStringIntoDocument(documentJson);
        if (isValid(document)) {
            System.out.println("[SERVER] Received new document: '" + document.title() + "' from " + document.sender());
            archiveDocument(document);
            dispatchToPrinters(document);
        }
    }

    private boolean isValid(Document document) {
        return document != null;
    }

    private void archiveDocument(Document document) {
        CompletableFuture.runAsync(() -> {
            System.out.println("[SERVER] Parallel Task: Archiving '" + document.title() + "' to the cloud...");
            SuperArchive.saveDocumentToCloud(document);
        });
    }

    private void dispatchToPrinters(Document document) {
        CompletableFuture.runAsync(() -> {
            System.out.println(
                    "[SERVER] Parallel Task: Splitting '" + document.title() + "' into pages and dispatching...");
            List<Page> pages = transform(document);
            String topic = selectTopic(document);
            String key = document.sender();
            sendPages(pages, topic, key);
        });
    }

    private List<Page> transform(Document document) {
        return SuperTransformer.transformDocumentIntoPages(document);
    }

    private String selectTopic(Document document) {
        return (document.type() == DocumentType.BLACK) ? Finals.TOPIC_BLACK : Finals.TOPIC_COLOR;
    }

    private void sendPages(List<Page> pages, String topic, String key) {
        for (Page page : pages) {
            String pageJson = convertToJson(page);
            KafkaProducerUtility.sendMessage(topic, key, pageJson);
        }
    }

    private String convertToJson(Page page) {
        try {
            return new ObjectMapper().writeValueAsString(page);
        } catch (Exception e) {
            return "";
        }
    }
}
