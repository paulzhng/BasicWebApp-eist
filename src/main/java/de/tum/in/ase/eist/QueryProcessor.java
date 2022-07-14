package de.tum.in.ase.eist;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QueryProcessor {

    private static final Pattern LARGEST_NUMBER_PATTERN = Pattern.compile("which of the following numbers is the largest: (.+)");

    public String process(String query) {
		query = query.toLowerCase();
        if (query.contains("shakespeare")) {
            return "William Shakespeare (26 April 1564 - 23 April 1616) was an " +
                    "English poet, playwright, and actor, widely regarded as the greatest " +
                    "writer in the English language and the world's pre-eminent dramatist.";
        } else if (query.contains("name")) {
           return "Paul";
        } else if (query.contains("what is") && query.contains("plus")){
            query = query.replace("what is", "").trim();
            String[] splitted = query.split("plus");
            if (splitted.length < 2) {
                return "";
            } else {
                try {
                    int a = Integer.parseInt(splitted[0].trim());
                    int b = Integer.parseInt(splitted[1].trim());
                    return String.valueOf(a + b);
                } catch (NumberFormatException e) {
                    return "";
                }
            }
        } else if (LARGEST_NUMBER_PATTERN.asPredicate().test(query)) {
            Matcher matcher = LARGEST_NUMBER_PATTERN.matcher(query);
            if (!matcher.find()) {
                return "";
            }

            String numbers = matcher.group(1);
            try {
                int max = Arrays.stream(numbers.split(","))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .max()
                        .orElse(0);
                return String.valueOf(max);
            } catch (NumberFormatException e) {
                return "";
            }
        }

        return "";
    }
}
