package de.tum.in.ase.eist;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class QueryProcessor {

    private static final Pattern ADDITION_PATTERN = Pattern.compile("what is (\\d+) plus (\\d+)");
    private static final Pattern MULTIPLICATION_PATTERN = Pattern.compile("what is (\\d+) multiplied by (\\d+)");
    private static final Pattern LARGEST_NUMBER_PATTERN = Pattern.compile("which of the following numbers is the largest: (.+)");
    private static final Pattern SQUARE_AND_CUBE_PATTERN = Pattern.compile("which of the following numbers is both a square and a cube: (.+)");

    public String process(String query) {
        query = query.toLowerCase();
        if (query.contains("shakespeare")) {
            return "William Shakespeare (26 April 1564 - 23 April 1616) was an " +
                    "English poet, playwright, and actor, widely regarded as the greatest " +
                    "writer in the English language and the world's pre-eminent dramatist.";
        } else if (query.contains("name")) {
            return "Paul";
        } else if (ADDITION_PATTERN.asPredicate().test(query)) {
            Matcher matcher = ADDITION_PATTERN.matcher(query);
            if (!matcher.find()) {
                return "";
            }

            int a = Integer.parseInt(matcher.group(1));
            int b = Integer.parseInt(matcher.group(2));
            return String.valueOf(a + b);
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
        } else if (MULTIPLICATION_PATTERN.asPredicate().test(query)) {
            Matcher matcher = MULTIPLICATION_PATTERN.matcher(query);
            if (!matcher.find()) {
                return "";
            }

            int a = Integer.parseInt(matcher.group(1));
            int b = Integer.parseInt(matcher.group(2));
            return String.valueOf(a * b);
        } else if (SQUARE_AND_CUBE_PATTERN.asPredicate().test(query)) {
            Matcher matcher = SQUARE_AND_CUBE_PATTERN.matcher(query);
            if (!matcher.find()) {
                return "";
            }

            String numbersString = matcher.group(1);
            return Arrays.stream(numbersString.split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .filter(n -> n > 0)
                    .filter(QueryProcessor::isSquareOrCube)
                    .boxed()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
        }

        return "";
    }

    private static final double EPS = 0.00001;

    private static boolean isSquareOrCube(int n) {
        double sqrt = Math.sqrt(n);
        double cube = Math.cbrt(n);
        return Math.abs((sqrt - (int) sqrt)) < EPS && Math.abs((cube - (int) cube)) < EPS;
    }

    public static void main(String[] args) {
        String answer = new QueryProcessor().process("0ddc4e10: what is 17 plus 13");
        System.out.println("answer = " + answer);
    }
}
