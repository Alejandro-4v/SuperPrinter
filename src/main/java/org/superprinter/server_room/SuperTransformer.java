package org.superprinter.server_room;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.superprinter.office.Document;
import org.superprinter.stationers_room.Page;
import org.superprinter.utils.Finals;

import java.util.ArrayList;
import java.util.List;

public class SuperTransformer {

    public static List<Page> transformDocumentIntoPages(Document documentToTransform) {
        String documentContent = documentToTransform.content();

        int totalDocumentCharacters = documentContent.length();
        int totalFullPages = totalDocumentCharacters / Finals.PAGE_SIZE;

        String documentTitle = documentToTransform.title();

        List<Page> transformedDocument = new ArrayList<>();

        for (int i = 0; i < totalFullPages; i++) {
            int indexToStartSplitting = i * Finals.PAGE_SIZE;
            int indexToStopSplitting = indexToStartSplitting + Finals.PAGE_SIZE;

            String pageContent = documentContent.substring(indexToStartSplitting, indexToStopSplitting);

            String pageTitle = documentTitle + "_" + i;
            Page newPage = new Page(pageTitle, pageContent);

            transformedDocument.add(newPage);
        }

        if (totalDocumentCharacters % Finals.PAGE_SIZE != 0) {
            int indexToStartSplitting = totalFullPages * Finals.PAGE_SIZE;

            String pageContent = documentContent.substring(indexToStartSplitting);

            String pageTitle = documentTitle + "_" + totalFullPages;
            Page newPage = new Page(pageTitle, pageContent);

            transformedDocument.add(newPage);
        }

        return transformedDocument;
    }

    public static String transformDocumentIntoJson(Document documentToTransform) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(documentToTransform);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static Document transformJsonStringIntoDocument(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(jsonString, Document.class);
        } catch (Exception e) {
            return null;
        }
    }

}
