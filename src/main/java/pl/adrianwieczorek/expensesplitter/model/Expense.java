package pl.adrianwieczorek.expensesplitter.model;

import java.util.List;

public class Expense {
    private final String description;
    private final long amount;
    private final Person paidBy;
    private final List<Person> participants;

    public Expense(String description, long amount, Person paidBy, List<Person> participants) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (paidBy == null) {
            throw new IllegalArgumentException("Paid by cannot be null");
        }
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("List of participants cannot be empty");
        }

        this.description = description.trim();
        this.amount = amount;
        this.paidBy = paidBy;
        this.participants = participants;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public Person getPaidBy() {
        return paidBy;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "DESCRIPTION: " + description + " AMOUNT: " + amount + " PAID BY: " + paidBy + " PARTICIPANTS: " + participants;
    }
}