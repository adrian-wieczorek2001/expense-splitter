package pl.adrianwieczorek.expensesplitter.util;

public class AmountParser {

    private AmountParser() { }

    private static boolean isDigitsOnly(String text) {
        if (text.isEmpty()) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // "12," is accepted as 12 zł
    public static long parseAmount(String text) {
        String[] parts = text.split(",");

        if (parts.length > 2) {
            throw new IllegalArgumentException("Invalid amount: " + text);
        }

        if (!isDigitsOnly(parts[0])) {
            throw new IllegalArgumentException("Invalid amount: " + text);
        }

        long amountInGrosze = Long.parseLong(parts[0]) * 100;

        if (parts.length == 2) {
            if (!isDigitsOnly(parts[1])) {
                throw new IllegalArgumentException("Invalid amount: " + text);
            }

            long fractionPart = Long.parseLong(parts[1]);

            if (parts[1].length() == 1) {
                fractionPart = fractionPart * 10;
            } else if (parts[1].length() > 2) {
                throw new IllegalArgumentException("Invalid amount: " + text);
            }
            amountInGrosze = amountInGrosze + fractionPart;
        }
        return amountInGrosze;
    }

}
