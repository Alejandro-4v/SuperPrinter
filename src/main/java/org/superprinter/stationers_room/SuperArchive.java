package org.superprinter.stationers_room;

import org.superprinter.office.Document;
import org.superprinter.utils.Directory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SuperArchive {

    public static void saveDocumentToCloud(Document document) {
        String pathForSavingTheDocument = getPathForSavingDocument(document);
        String documentContent = document.content();

        File folder = getFolder(pathForSavingTheDocument);

        File file = new File(folder, document.title() + ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(documentContent);
            writer.flush();
        } catch (IOException e) {
            return;
        }
    }

    private static File getFolder(String pathForSavingTheDocument) {
        File folder = new File(pathForSavingTheDocument);
        checkFolderExists(folder);
        return folder;
    }

    private static void checkFolderExists(File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private static String getPathForSavingDocument(Document document) {
        String rootPathForDocuments = Directory.OFFICE_DOCUMENTS_PATH;

        return rootPathForDocuments + "/" + document.sender();
    }

}
