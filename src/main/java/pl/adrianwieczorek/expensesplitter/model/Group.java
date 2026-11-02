package pl.adrianwieczorek.expensesplitter.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final String name;
    private final List<Person> members;
    private final List<Expense> expenses;

    public Group(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be empty");
        }
        this.name = name.trim();
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    public List<Person> getMembers() {
        return List.copyOf(members);
    }

    public void addMember(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        if (members.contains(person)) {
            throw new IllegalStateException("Person is already on the list");
        }
        members.add(person);
    }

    public void addExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        if (!members.contains(expense.getPaidBy())) {
            throw new IllegalArgumentException("Payer is not a member of the group");
        }
        for (Person participant : expense.getParticipants()) {
            if (!members.contains(participant)) {
                throw new IllegalArgumentException("Participant is not a member of the group: " + participant);
            }
        }
        expenses.add(expense);
    }
}
