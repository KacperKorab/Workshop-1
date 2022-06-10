package pl.coderslab;


/*
TODO wyświetlanie wszystkich dostępnych zadań,
TODO wyjście z aplikacji,
TODO dodanie zadania,
TODO usuwanie zadania,
TODO wczytywanie danych z pliku przy starcie aplikacji,
TODO zapis danych do pliku,
TODO sprawdzanie poprawność wartości liczbowej podczas usuwania.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;

public class TaskManager {
    public static void main(String[] args) {
        System.out.println(ConsoleColors.CYAN_BOLD + "WELCOME!");
        System.out.println(ConsoleColors.RESET);

        List<List<String>> records = csvIntoList();// CSV Into ArrayList with print

        // User Menu
        String[] options = {"1- Add 1", "2- Remove 2", "3- List 3", "4- Exit"};
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        while (option != 4){ // menu loop
            printMenu(options); //print menu method
                try {
                option = scanner.nextInt();
                    switch (option) {// Menu Options
                        case 1 -> add();
                        case 2 -> remove();
                        case 3 -> list(records);
                        case 4 -> {System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"Program terminated by user");exit(0);}
                    }
                    if (option > options.length) { // User selected option larger than 4
                        System.out.println(ConsoleColors.RED+"Incorrect input! Please enter an integer value between 1 and " + options.length);
                        System.out.print(ConsoleColors.RESET);
                    }
                }
            catch (InputMismatchException ex){ // User input other than int
                System.out.println(ConsoleColors.RED+"Incorrect input! Please enter an integer value between 1 and " + options.length);
                System.out.print(ConsoleColors.RESET);
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
                scanner.next();
            }
        } // User Menu end
    }

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
    public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    // Menu Options
    private static void add() {
        System.out.println("You chose option 1.");
    }
    private static void remove() {
        System.out.println("You chose option 2.");
    }
    private static void list(List<List<String>> records) {
        System.out.println("You chose option 3.");
        for (List<String> printList : records) {
            System.out.println(printList);
        }
    }
}

