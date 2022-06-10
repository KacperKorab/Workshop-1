package pl.coderslab;

/*
DONE wyświetlanie wszystkich dostępnych zadań,
DONE wyjście z aplikacji,
DONE dodanie zadania, DONE check for valid date input
DONE usuwanie zadania,
DONE wczytywanie danych z pliku przy starcie aplikacji,
DONE zapis danych do pliku,
TODO sprawdzanie poprawność wartości liczbowej podczas usuwania. ?

TODO beautification

TODO Bugfixing: testing
TODO Bugfixing: check for int inside backToMenu
TODO Bugfixing: improper input in Add true/false
 */

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.exit;

public class TaskManager {
    public static void main(String[] args) {
        System.out.println(ConsoleColors.CYAN_BOLD + "WELCOME!");
        System.out.println(ConsoleColors.RESET);

        List<List<String>> records = csvIntoList();// CSV Into ArrayList with print

        // User Menu
        String[] options = {"1- Add", "2- Remove", "3- List", "4- Exit"};
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (option != 4) { // menu loop
            printMenu(options); //print menu method
            try {
                option = scanner.nextInt();
                switch (option) {// Menu Options
                    case 1 -> add(records);
                    case 2 -> remove(records);
                    case 3 -> list(records);
                    case 4 -> save(records);

                }
                if (option > options.length || option < 1) { // User selected option larger than 4
                    System.out.println(ConsoleColors.RED + "Incorrect input! Please enter an integer value between 1 and " + options.length);
                    System.out.print(ConsoleColors.RESET);
                }
            }
            catch (InputMismatchException ex) { // User input other than int
                System.out.println(ConsoleColors.RED + "Incorrect input! Please enter an integer");
                System.out.print(ConsoleColors.RESET);
                scanner.next();
            }
            catch (Exception ex) {
                System.out.println("An unexpected error happened. Please try again");
                scanner.next();
            }
        }
    }

    // Turns selected file into a list, used in main.
    private static List<List<String>> csvIntoList() { // CSV Into ArrayList with print
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("tasks.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    // User Menu
    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    // Menu Option "Add"
    // Adds tasks based on user input
    private static void add(List<List<String>> records) {
        System.out.println("Enter task name:");

        Scanner addScanner = new Scanner(System.in);
        List<String> taskList = new ArrayList<>();
        String userInput = String.valueOf(addScanner.nextLine());
        taskList.add(userInput);

        System.out.println("Enter task deadline in yyyy-mm-dd format:");
        String addDate;
        while (true) {
            addDate = String.valueOf(addScanner.nextLine());
            if ((isValidDate(addDate)) && isFutureDate(addDate)) {
                break;
            } else {
                System.out.println(ConsoleColors.RED+"Bad input. The date must occur after today and be in the correct format.");
                System.out.println(ConsoleColors.RESET+"Enter task deadline in yyyy-mm-dd format:");
            }
        }
        taskList.add(addDate);

        System.out.println("Is this task important? Enter '1' for yes or '2' for no.");
        int addTF = 0;
        while (addTF != 1 && addTF != 2) {
            addTF = addScanner.nextInt();
            switch (addTF) {
                case 1 -> taskList.add("true");
                case 2 -> taskList.add("false");
            }
        }
        records.add(taskList);
    }

    // Menu Option "Remove"
    // Removes data by list index
    private static void remove(List<List<String>> records) {
        System.out.println("To remove a task enter it's number:");
        Scanner removeScanner = new Scanner(System.in);
        int userInput = (removeScanner.nextInt());
        records.remove(userInput);
    }

    // Menu Option "List"
    // Displays data list with indexes and waits for user to return to menu
    private static void list(List<List<String>> records) {
        for (int i = 0; i < records.size(); i++) {
            List<String> printList = records.get(i);
            System.out.println(i + " : " + printList);
        }
        backToMenu();
    }

    // Menu Option "Exit"
    // Overwrites "tasks.csv" with updated data list
    private static void save(List<List<String>> records) {
        try {
            Writer fileWriter = new FileWriter("tasks.csv", false);
            for (int i = 0; i < records.size(); i++) {
                List<String> printList = records.get(i);
                if (i>0) {
                    fileWriter.write("\n");
                }
                for (String s : printList) {
                    fileWriter.write(s + ",");
                }
            }
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Program terminated by user");
        exit(0);
    }

    // Checks for proper user input when getting date in Add method
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        }
        catch (ParseException pe) {
            return false;
        }
        return true;
    }

    // Checks whether date entered by user is in the future, used in Add method
    public static boolean isFutureDate(String args) {
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = null;
        try {
            inputDate = sdformat.parse(args);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ConsoleColors.RED+"The input date is: " + sdformat.format(inputDate));
        System.out.println("The current date is: " + sdformat.format(date));
        System.out.print(ConsoleColors.RESET);
        assert inputDate != null;
        return inputDate.compareTo(date) > 0;
    }

    //Waits for user input, then goes back to menu, used in List method
    private static void backToMenu() {
        System.out.println("Press 4 to go back to menu.");
        Scanner listScanner = new Scanner(System.in);
        int option = 0;
        while (option != 4) {
            option = listScanner.nextInt();
            if (option != 4) { // User selected option other than 4
                System.out.println(ConsoleColors.RED + "Incorrect input! Press 4 to go back to menu.");
                System.out.print(ConsoleColors.RESET);
            }
        }
    }
}

