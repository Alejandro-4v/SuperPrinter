package org.superprinter.office;

import org.superprinter.library.Librarian;
import org.superprinter.server_room.Server;
import org.superprinter.utils.Directory;
import org.superprinter.utils.Finals;

import java.util.List;
import java.util.Random;

public class Office {

    private static final Random RND = new Random();

    private static List<Thread> EMPLOYEES;
    private static Server MAIN_SERVER;
    private static Librarian OFFICE_LIBRARIAN;

    public static void main(String[] args) {

        welcomeLibrarian();

        startMainServer();

        welcomeEmployees();

        getEmployeesToWork();

    }

    private static void welcomeEmployees() {
        for (int i = 0; i < Finals.EMPLOYEES_COUNT; i++) {
            Employee newEmployee = new Employee("employee_" + i, MAIN_SERVER, OFFICE_LIBRARIAN);
            Thread newEmployeeThread = new Thread(newEmployee);
            EMPLOYEES.add(newEmployeeThread);
        }
    }

    private static void getEmployeesToWork() {
        for (Thread employee : EMPLOYEES) {
            employee.start();
        }
    }

    private static void startMainServer() {
        MAIN_SERVER = new Server();
    }

    private static void welcomeLibrarian() {
        OFFICE_LIBRARIAN = new Librarian("Teresa", Directory.LIBRARY_BOOKS_PATH);
    }

}
