package org.superprinter.server_room;

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

}
