package de.tum.in.ase.eist;

public class SimpleAnswerProvider implements AnswerProvider {

    private final String question;
    private final String answer;

    public SimpleAnswerProvider(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String process(String query) {
        if (query.toLowerCase().contains(question.toLowerCase())) {
            return answer;
        } else {
            return "";
        }
    }
}
