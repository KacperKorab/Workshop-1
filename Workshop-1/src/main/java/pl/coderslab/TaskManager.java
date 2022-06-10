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

import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.System.exit;

public class TaskManager {
    public static void main(String[] args) {
        System.out.println(ConsoleColors.CYAN_BOLD + "WELCOME!");
        System.out.println(ConsoleColors.RESET);

        // Menu Navigation
        String[] options = {"1- Option 1", "2- Option 2", "3- Option 3", "4- Exit"};
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 4){ // menu loop
            printMenu(options); //print menu method
                try {
                option = scanner.nextInt();
                    switch (option) {
                        case 1 -> option1();
                        case 2 -> option2();
                        case 3 -> option3();
                        case 4 -> {System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"Program terminated by user");exit(0);}
                    }
                    if (option > options.length) {
                        System.out.println(ConsoleColors.RED+"Incorrect input! Please enter an integer value between 1 and " + options.length);
                        System.out.print(ConsoleColors.RESET);
                    }
                }
            catch (InputMismatchException ex){
                System.out.println(ConsoleColors.RED+"Incorrect input! Please enter an integer value between 1 and " + options.length);
                System.out.print(ConsoleColors.RESET);
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
                scanner.next();
            }
        }
    }

    // Menu Options
    private static void option1() {
        System.out.println("You chose option 1.");
    }
    private static void option2() {
        System.out.println("You chose option 2.");
    }
    private static void option3() {
        System.out.println("You chose option 3.");
    }

    // User Menu
    public static void printMenu(String[] options){
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}

