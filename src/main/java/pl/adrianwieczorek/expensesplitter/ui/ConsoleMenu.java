package pl.adrianwieczorek.expensesplitter.ui;

import pl.adrianwieczorek.expensesplitter.model.Expense;
import pl.adrianwieczorek.expensesplitter.model.Group;
import pl.adrianwieczorek.expensesplitter.model.Person;
import pl.adrianwieczorek.expensesplitter.model.Transfer;
import pl.adrianwieczorek.expensesplitter.service.ExpenseService;
import pl.adrianwieczorek.expensesplitter.util.AmountParser;

import javax.management.Descriptor;
import java.util.*;

public class ConsoleMenu {
    private final Scanner scanner;
    private final ExpenseService expenseService;
    private final Group group;

    public ConsoleMenu(ExpenseService expenseService, Group group) {
        this.scanner = new Scanner(System.in);
        this.expenseService = Objects.requireNonNull(expenseService, "ExpenseService cannot be null");
        this.group = Objects.requireNonNull(group, "Group cannot be null");
    }

    private int readChoice() {
        while(true) {
            System.out.print("Choose an option: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Choice must be a number");
            }
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                long number = scanner.nextLong();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("It must be a number");
            }
        }
    }

    private long readAmount(String prompt) {
        while (true) {
            try {
                String textToConvert  = readText(prompt);
                long amount = AmountParser.parseAmount(textToConvert);
                return amount;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() +
                        "\nUse format 12,50");
            }
        }
    }

    private List<Person> readParticipants() {
        List<Person> participants = new ArrayList<>();
        boolean addMore = true;
        while (addMore) {
           String name = readText("Participant name: ");
           Person person = new Person(name);
           participants.add(person);
           String answer = readText("Do you want to add another person? (y/n): ");
           if (answer.equals("n")) {
               addMore = false;
           }
        }
        return participants;
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readChoice();
            switch (choice) {
                case 1 -> showBalances();
                case 2 -> showTransfers();
                case 3 -> addMember();
                case 4 -> addExpense();
                case 0 -> running = false;
                default -> System.out.println("Unknown option, try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("""
                    1. Show balances
                    2. Show transfers
                    3. Add member
                    4. Add expense
                    0. Exit
                    """);
    }

    private Map<Person, Long> calculateGroupBalances() {
        return expenseService.calculateBalances(group);
    }

    private void showBalances() {
        Map<Person, Long> balances = calculateGroupBalances();
        balances.forEach((person, balance) -> System.out.println(person + ": " + balance));
    }

    private void showTransfers() {
        Map<Person, Long> balances = calculateGroupBalances();
        List<Transfer> transfers = expenseService.settleBalances(balances);
        for (Transfer transfer : transfers) {
            System.out.println(transfer);
        }
    }

    private void addMember() {
        String name = readText("Type a name of member: ");
        try {
            Person person = new Person(name);
            group.addMember(person);
            System.out.println("Added person: " + person);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addExpense() {
        String description = readText("Description: ");
        long amount = readAmount("Amount (PLN): ");
        try {
            String payerName = readText("Paid by: ");
            Person payer = new Person(payerName);
            List<Person> participants = readParticipants();
            Expense expense = new Expense(description, amount, payer, participants);
            group.addExpense(expense);
            System.out.println("Added expense: " + description);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
