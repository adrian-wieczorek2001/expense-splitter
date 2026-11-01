package pl.adrianwieczorek.expensesplitter;

import pl.adrianwieczorek.expensesplitter.model.Expense;
import pl.adrianwieczorek.expensesplitter.model.Person;
import pl.adrianwieczorek.expensesplitter.service.ExpenseService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Expense> createSampleExpenses() {
        Person adam = new Person("Adam");
        Person bartek = new Person("Bartek");
        Person celina = new Person("Celina");

        List<Expense> expenses = new ArrayList<>();

        expenses.add(new Expense("nocleg", 30000, adam, List.of(adam, bartek, celina)));
        expenses.add(new Expense("jedzenie", 9000, bartek, List.of(adam, bartek, celina)));
        expenses.add(new Expense("paliwo", 6000, celina, List.of(celina, adam)));

        return expenses;
    }

    public static void main(String[] args) {
        List<Expense> expenses = createSampleExpenses();
        ExpenseService expenseService = new ExpenseService();

        for (Expense expense : expenses) {
            System.out.println(expense);
        }

        System.out.println(expenseService.splitExpense(expenses.getFirst()));
    }
}
