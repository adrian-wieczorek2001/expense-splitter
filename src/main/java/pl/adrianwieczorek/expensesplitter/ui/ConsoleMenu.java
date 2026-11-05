package pl.adrianwieczorek.expensesplitter.ui;

import pl.adrianwieczorek.expensesplitter.model.Expense;
import pl.adrianwieczorek.expensesplitter.model.Group;
import pl.adrianwieczorek.expensesplitter.model.Person;
import pl.adrianwieczorek.expensesplitter.model.Transfer;
import pl.adrianwieczorek.expensesplitter.service.ExpenseService;

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

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readChoice();
            switch (choice) {
                case 1 -> showBalances();
                case 2 -> showTransfers();
                case 3 -> addMember();
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
                    0. Exit
                    """);
    }

    private Map<Person, Long> calculateGroupBalances() {
        List<Expense> expenses = group.getExpenses();
        return expenseService.calculateBalances(expenses);
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

}
