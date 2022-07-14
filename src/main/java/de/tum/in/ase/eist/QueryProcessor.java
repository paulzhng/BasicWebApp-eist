package de.tum.in.ase.eist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryProcessor {

    private final List<AnswerProvider> answerProviders;

    @Autowired
    public QueryProcessor(List<AnswerProvider> answerProviders) {
        this.answerProviders = answerProviders;
    }

    public String process(String query) {
        query = query.toLowerCase();

        for (AnswerProvider answerProvider : answerProviders) {
            try {
                String answer = answerProvider.process(query);
                if (!answer.isEmpty()) {
                    return answer;
                }
            } catch (Exception ignored) {
            }
        }

        return "";
    }


}
