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
    public Map<Person, Long> calculateBalances(List<Expense> expenses) {
        Map<Person, Long> balances = new HashMap<>();

        for (Expense expense : expenses) {
            Person payer = expense.getPaidBy();
            long currentBalance = balances.getOrDefault(payer, 0L);
            long balance = currentBalance + expense.getAmount();
            balances.put(payer, balance);
            Map<Person, Long> split = splitExpense(expense);
            for (Person person : split.keySet()) {
                long share = split.get(person);
                long currentBalancePerson = balances.getOrDefault(person, 0L);
                long balancePerson = currentBalancePerson - share;
                balances.put(person, balancePerson);
            }
        }
        return balances;
    }
}
