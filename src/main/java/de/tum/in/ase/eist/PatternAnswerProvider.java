package de.tum.in.ase.eist;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternAnswerProvider implements AnswerProvider {

    private final Pattern pattern;
    private final Function<Matcher, String> matcherFunction;

    public PatternAnswerProvider(String pattern, Function<Matcher, String> matcherFunction) {
        this.pattern = Pattern.compile(pattern);
        this.matcherFunction = matcherFunction;
    }

    public String process(String query) {
        Matcher matcher = pattern.matcher(query);
        if (!matcher.find()) {
            return "";
        }
        return matcherFunction.apply(matcher);
    }
}
