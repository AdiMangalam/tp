//@@author glenda-1506
package seedu.spendswift.parser;

import seedu.spendswift.ui.ErrorMessage;

public class InputParser {
    private String parseComponent(String input, String prefix) {
        int startIndex = input.indexOf(prefix);
        if (startIndex == -1) {
            return "";
        }

        startIndex += prefix.length();
        int endIndex = input.length();
        String[] prefixes = {"n/", "a/", "c/", "e/", "l/"};

        for (String otherPrefix : prefixes) {
            if (!otherPrefix.equals(prefix)) {
                int prefixIndex = input.indexOf(otherPrefix, startIndex);
                if (prefixIndex != -1 && prefixIndex < endIndex) {
                    endIndex = prefixIndex;
                }
            }
        }

        return input.substring(startIndex, endIndex).trim();
    }

    public int parseIndex(String input) {
        String indexStr = parseComponent(input, "e/");
        try {
            return Integer.parseInt(indexStr) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double parseLimit(String input) {
    String limitStr = parseComponent(input, "l/");
    try {
        double limit = Double.parseDouble(limitStr);

        // Assertion to ensure the limit does not exceed 1 quadrillion
        assert limit <= 1_000_000_000_000_000.0 : "Limit exceeds 1 quadrillion, capping to 1 quadrillion";
        
        // Cap the limit at 1 quadrillion if it exceeds this value
        if (limit > 1_000_000_000_000_000.0) {
            limit = 1_000_000_000_000_000.0;
        }

        return limit;
    } catch (NumberFormatException e) {
        ErrorMessage.printInvalidLimit();
        return Double.NaN;
    }
}

    public double parseAmount(String input) {
        String amountStr = parseComponent(input, "a/");
        try {
            return Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            ErrorMessage.printInvalidAmount();
            return Double.NaN;
        }
    }

    public String parseName(String input) {
        return parseComponent(input, "n/");
    }

    public String parseCategory(String input) {
        return parseComponent(input, "c/");
    }
}
