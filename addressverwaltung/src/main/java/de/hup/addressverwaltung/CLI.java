package de.hup.addressverwaltung;

import java.util.Optional;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner;

    public CLI() {
        this.scanner = new Scanner(System.in);
    }

    public State menu() {
        Optional<State> state = Optional.empty();
        var isValidAction = false;
        while (!isValidAction) {
            System.out.print("""
                    Options:
                    1. Add a new address \t [Enter 'add address' to add]
                    2. List all the addresses \t [Enter 'list' to list]
                    3. Exit the program \t [Enter 'exit' to exit]
                    4. Delete address \t [Enter 'kill' to kill]
                    5. Edit smth \t [Enter 'edit' to edit]
                    6. Add a new person \t [Enter 'detect' to add human]
                    """);
            String nextLine = scanner.nextLine().trim();

            switch (nextLine) {
                case "add address" -> state = Optional.of(State.ADDNEWADDRESS);
                case "list" -> state = Optional.of(State.LIST);
                case "exit" -> state = Optional.of(State.EXIT);
                case "kill" -> state = Optional.of(State.KILL);
                case "edit" -> state = Optional.of(State.EDIT);
                case "detect" -> state = Optional.of(State.DETECT);
                case "add person" -> state = Optional.of(State.ADDNEWADDRESS);
                default -> System.out.println("please enter a valid option");
            }

            if (state.isPresent()) {
                isValidAction = true;
            }
        }
        return state.get();
    }

    public String editUserMenu() {
        System.out.println("let me what u wanna edit..");
            System.out.print("""
                    Options:
                    1. edit street \t [Enter 'street' to street]
                    2. edit postfach \t [Enter 'postfaach' to postfach]
                    3. change plz \t [Enter 'plz' to plz]
                    4. to change stadt \t [Enter 'stadt' to stadt]
                    """);
            return scanner.nextLine();

    }

    public int promptId() {
        while (true) {
            System.out.print("Enter id: ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid id.");
            }
        }
    }

    public String promptStreet() {
        var input = "️🚩️";

        do {
            if (!input.equals("️🚩️")) {
                System.out.println("Input cannot be empty. Please try again.");
            }

            System.out.print("Enter Str and Hausnummer: ");
            input = scanner.nextLine().trim().replaceAll("\\s+", " ");
        } while (input.isEmpty());

        return input;
    }

    public int promptPostfach() {
        while (true) {
            System.out.print("Enter Postfach: ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public int promptPlz() {
        while (true) {
            System.out.print("Enter PLZ: ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public String promptOrt() {
        while (true) {
            System.out.print("Enter Ort: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public String promptPersonName(){
        System.out.print("Enter ur nameЮ ");
        return scanner.nextLine().trim();
    }

    public static int askToDo()
    {
        System.out.println("Do u wanna smth else edit?????????");
        System.out.println("If u wanna change at this id smth else change press [1]\n" +
                "If u wanna change at another id press [2]\n" +
                "Or if u wanna go back to main menu press Enter on your keyboard");
        String scanner = new Scanner(System.in).nextLine();

        if (scanner.equalsIgnoreCase("1")) {
            return 1;
        }

        if (scanner.equalsIgnoreCase("2")) {
            return 2;
        }

        return 3;
    }
}
