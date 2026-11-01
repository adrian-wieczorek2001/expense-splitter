package pl.adrianwieczorek.expensesplitter.service;

import pl.adrianwieczorek.expensesplitter.model.Expense;
import pl.adrianwieczorek.expensesplitter.model.Person;
import pl.adrianwieczorek.expensesplitter.model.Transfer;

import java.util.*;

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

    private boolean hasNonZeroBalance(Map<Person, Long> balances) {
        for (long value : balances.values()) {
            if (value != 0) {
                return true;
            }
        }
        return false;
    }

    private Optional<Person> findDebtor(Map<Person, Long> balances) {
        for (Map.Entry<Person, Long> entry : balances.entrySet()) {
            if (entry.getValue() < 0) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    private Optional<Person> findCreditor(Map<Person, Long> balances) {
        for (Map.Entry<Person, Long> entry : balances.entrySet()) {
            if (entry.getValue() > 0) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    public List<Transfer> settleBalances(Map<Person, Long> balances) {
        var balancesCopy = new LinkedHashMap<>(balances);

        List<Transfer> transfersList = new ArrayList<>();

        while (hasNonZeroBalance(balancesCopy)) {
           Person creditor = findCreditor(balancesCopy).orElseThrow();
           Person debtor = findDebtor(balancesCopy).orElseThrow();

           long debtorBalance = balancesCopy.get(debtor);
           long creditorBalance = balancesCopy.get(creditor);

           long amount = Math.min(-debtorBalance, creditorBalance);

           transfersList.add(new Transfer(debtor, creditor, amount));
           balancesCopy.put(creditor, creditorBalance - amount);
           balancesCopy.put(debtor, debtorBalance + amount);
        }
        return transfersList;
    }

}
