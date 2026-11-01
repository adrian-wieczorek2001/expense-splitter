package pl.adrianwieczorek.expensesplitter.model;

public class Transfer {

    private final Person fromPerson;
    private final Person toPerson;
    private final long amount;

    public Transfer(Person fromPerson, Person toPerson, long amount) {
        if (fromPerson == null || toPerson == null) {
            throw new IllegalArgumentException("Transfer persons cannot be null");
        }
        if (fromPerson.equals(toPerson)) {
            throw new IllegalArgumentException("Cannot transfer to the same person");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.amount = amount;
    }

    public Person getFromPerson() {
        return fromPerson;
    }

    public Person getToPerson() {
        return toPerson;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return fromPerson + " --> " + toPerson + ": " + amount;
    }
}
