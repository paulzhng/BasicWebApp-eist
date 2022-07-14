package de.tum.in.ase.eist;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class QueryProcessor {

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
        } else if (query.contains("which of the following numbers is the largest: ")) {
            Pattern pattern = Pattern.compile("which of the following numbers is the largest: (.+)");

        }

        return "";
    }
}
