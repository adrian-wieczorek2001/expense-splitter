package pl.adrianwieczorek.expensesplitter;

import pl.adrianwieczorek.expensesplitter.model.Expense;
import pl.adrianwieczorek.expensesplitter.model.Person;
import pl.adrianwieczorek.expensesplitter.service.ExpenseService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println(expenseService.calculateBalances(expenses));

        Map<Person, Long> balances = expenseService.calculateBalances(expenses);
        System.out.println(expenseService.settleBalances(balances));

        System.out.println();

        Person ania = new Person("Ania");
        Person bartek2 = new Person("Bartek");
        Person celina2 = new Person("Celina");
        Person darek = new Person("Darek");

        Map<Person, Long> test = new LinkedHashMap<>();
        test.put(ania, 5000L);
        test.put(bartek2, 3000L);
        test.put(celina2, -6000L);
        test.put(darek, -2000L);

        System.out.println(expenseService.settleBalances(test));

        System.out.println();

        Person a1 = new Person("Adam");
        Person a2 = new Person("Adam");
        System.out.println(a1 == a2);
        System.out.println(a1.equals(a2));
    }
}
