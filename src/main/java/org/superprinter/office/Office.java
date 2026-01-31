package org.superprinter.office;

import org.superprinter.library.Librarian;
import org.superprinter.server_room.KafkaProducerUtility;
import org.superprinter.server_room.Server;
import org.superprinter.stationers_room.Printer;
import org.superprinter.utils.Directory;
import org.superprinter.utils.DocumentType;
import org.superprinter.utils.Finals;

import java.util.ArrayList;
import java.util.List;

public class Office {

    private static final List<Thread> EMPLOYEES = new ArrayList<>();
    private static Server MAIN_SERVER;
    private static Librarian OFFICE_LIBRARIAN;

    public static void main(String[] args) {

        openThenLibrary();

        startTheMainServerAndKafka();

        setupThePrinters();

        welcomeEmployees();

        startTheWorkingDay();

    }

    private static void openThenLibrary() {
        System.out.println("[OFFICE] Opening the library and welcoming our librarian...");
        OFFICE_LIBRARIAN = new Librarian("Teresa", Directory.LIBRARY_BOOKS_PATH);
    }

    private static void startTheMainServerAndKafka() {
        System.out.println("[OFFICE] Warming up the main server and Kafka producer...");
        MAIN_SERVER = new Server();
        KafkaProducerUtility.initialize();
    }

    private static void setupThePrinters() {
        System.out.println("[OFFICE] Calibrating the printers (" + Finals.BLACK_AND_WHITE_PRINTERS_COUNT + " B/W, "
                + Finals.COLOR_PRINTERS_COUNT + " Color)...");
        for (int i = 0; i < Finals.BLACK_AND_WHITE_PRINTERS_COUNT; i++) {
            startPrinter(DocumentType.BLACK);
        }
        for (int i = 0; i < Finals.COLOR_PRINTERS_COUNT; i++) {
            startPrinter(DocumentType.COLOR);
        }
    }

    private static void startPrinter(DocumentType type) {
        Printer printer = new Printer(type);
        new Thread(printer).start();
    }

    private static void welcomeEmployees() {
        System.out.println("[OFFICE] Welcoming " + Finals.EMPLOYEES_COUNT + " employees to their desks...");
        for (int i = 0; i < Finals.EMPLOYEES_COUNT; i++) {
            Employee newEmployee = new Employee("employee_" + i, MAIN_SERVER, OFFICE_LIBRARIAN);
            Thread newEmployeeThread = new Thread(newEmployee);
            EMPLOYEES.add(newEmployeeThread);
        }
    }

    private static void startTheWorkingDay() {
        System.out.println("[OFFICE] The working day has officially started!");
        for (Thread employee : EMPLOYEES) {
            employee.start();
        }
    }

}
