package pl.adrianwieczorek.expensesplitter.model;

public class Person {
    private final String name;

    public Person(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("ERROR: Name cannot be null or blank");
        } else {
            this.name = name.trim();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person other)) return false;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
