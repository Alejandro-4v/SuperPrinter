package org.superprinter.office;

import org.superprinter.library.Librarian;
import org.superprinter.server_room.Server;
import org.superprinter.utils.Directory;
import org.superprinter.utils.Finals;

import java.util.ArrayList;
import java.util.List;

public class Office {

    private static final List<Thread> EMPLOYEES = new ArrayList<>();
    private static Server MAIN_SERVER;
    private static Librarian OFFICE_LIBRARIAN;

    public static void main(String[] args) {

        welcomeLibrarian();

        startMainServer();

        welcomeEmployees();

        getEmployeesToWork();

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
