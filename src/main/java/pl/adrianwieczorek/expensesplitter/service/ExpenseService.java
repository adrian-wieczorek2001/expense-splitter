package pl.adrianwieczorek.expensesplitter.service;

import pl.adrianwieczorek.expensesplitter.model.Expense;
import pl.adrianwieczorek.expensesplitter.model.Person;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService {
    public Map<Person, Long> splitExpense(Expense expense) {

        List<Person> participants = expense.getParticipants();
        long share = expense.getAmount() / participants.size();
        long remainder = expense.getAmount() % participants.size();

        Map<Person, Long> result = new LinkedHashMap<>();

        for (int i = 0; i < participants.size(); i++) {
            Person person = participants.get(i);

            if (i < remainder) {
                result.put(person, share + 1);
            } else {
                result.put(person, share);
            }
        }
        return result;
    }
}
