package pl.adrianwieczorek.expensesplitter;

import pl.adrianwieczorek.expensesplitter.model.Person;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Adam");
        Person person2 = new Person("Bartek");
        Person person3 = new Person("Cecylia");

        System.out.println(person1.getName());
        System.out.println(person2.getName());
        System.out.println(person3.getName());

    }
}
