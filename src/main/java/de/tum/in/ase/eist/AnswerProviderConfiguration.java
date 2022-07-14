package de.tum.in.ase.eist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class AnswerProviderConfiguration {

    @Bean
    public AnswerProvider shakespeareAnswerProvider() {
        return new SimpleAnswerProvider("shakespeare", "William Shakespeare (26 April 1564 - 23 April 1616) was an " +
                "English poet, playwright, and actor, widely regarded as the greatest " +
                "writer in the English language and the world's pre-eminent dramatist.");
    }

    @Bean
    public AnswerProvider nameAnswerProvider() {
        return new SimpleAnswerProvider("name", "Paul");
    }

    @Bean
    public AnswerProvider theresMayAnswerProvider() {
        return new SimpleAnswerProvider("which year was Theresa May first elected as the Prime Minister of Great Britain", "2016");
    }

    @Bean
    public AnswerProvider eiffelTowerAnswerProvider() {
        return new SimpleAnswerProvider("which city is the Eiffel tower in", "Paris");
    }

    @Bean
    public AnswerProvider jamesBondAnswerProvider() {
        return new SimpleAnswerProvider("who played James Bond in the film Dr No", "Sean Connery");
    }

    @Bean
    public AnswerProvider bananaAnswerProvider() {
        return new SimpleAnswerProvider("what colour is a banana", "yellow");
    }

    @Bean
    public AnswerProvider additionAnswerProvider() {
        return new PatternAnswerProvider("what is (\\d+) plus (\\d+)", matcher -> {
            int a = Integer.parseInt(matcher.group(1));
            int b = Integer.parseInt(matcher.group(2));
            return String.valueOf(a + b);
        });
    }

    @Bean
    public AnswerProvider multiplicationAnswerProvider() {
        return new PatternAnswerProvider("what is (\\d+) multiplied by (\\d+)", matcher -> {
            int a = Integer.parseInt(matcher.group(1));
            int b = Integer.parseInt(matcher.group(2));
            return String.valueOf(a * b);
        });
    }

    @Bean
    public AnswerProvider largestNumberAnswerProvider() {
        return new PatternAnswerProvider("which of the following numbers is the largest: (.+)", matcher -> {
            String numbers = matcher.group(1);
            int max = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0);
            return String.valueOf(max);
        });
    }

    @Bean
    public AnswerProvider squareAndCubeAnswerProvider() {
        return new PatternAnswerProvider("which of the following numbers is both a square and a cube: (.+)", matcher -> {
            String numbers = matcher.group(1);
            int max = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .filter(AnswerProviderConfiguration::isSquareOrCube)
                    .max()
                    .orElse(0);
            return String.valueOf(max);
        });
    }

    @Bean
    public AnswerProvider primePatternProvider() {
        return new PatternAnswerProvider("which of the following numbers is a prime number: (.+)", matcher -> {
            String numbers = matcher.group(1);
            int max = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .filter(AnswerProviderConfiguration::isPrime)
                    .max()
                    .orElse(0);
            return String.valueOf(max);
        });
    }

    private static final double EPS = 0.00001;
    private static boolean isSquareOrCube(int n) {
        double sqrt = Math.sqrt(n);
        double cube = Math.cbrt(n);
        return Math.abs((sqrt - (int) sqrt)) < EPS && Math.abs((cube - (int) cube)) < EPS;
    }

    private static boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
