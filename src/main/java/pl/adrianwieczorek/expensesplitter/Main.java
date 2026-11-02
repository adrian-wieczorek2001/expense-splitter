package pl.adrianwieczorek.expensesplitter;

import pl.adrianwieczorek.expensesplitter.model.Expense;
import pl.adrianwieczorek.expensesplitter.model.Group;
import pl.adrianwieczorek.expensesplitter.model.Person;
import pl.adrianwieczorek.expensesplitter.service.ExpenseService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static Group createSampleGroup() {
        Group group = new Group("Zakopane");

        Person adam = new Person("Adam");
        Person bartek = new Person("Bartek");
        Person celina = new Person("Celina");

        group.addMember(adam);
        group.addMember(bartek);
        group.addMember(celina);
        
        group.addExpense(new Expense("nocleg", 30000, adam, List.of(adam, bartek, celina)));
        group.addExpense(new Expense("jedzenie", 9000, bartek, List.of(adam, bartek, celina)));
        group.addExpense(new Expense("paliwo", 6000, celina, List.of(celina, adam)));

        return group;
    }

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
        ExpenseService expenseService = new ExpenseService();
        Group group = createSampleGroup();

        Map<Person, Long> balances = expenseService.calculateBalances(group.getExpenses());
        System.out.println(expenseService.settleBalances(balances));
    }
}
