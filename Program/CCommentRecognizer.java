public class CCommentRecognizer {

    private enum State {
        START, // q0: initial state
        SEEN_SLASH, // q1: seen opening '/'
        BODY, // q2: inside comment body (last char was 'a' or '/')
        SEEN_STAR, // q3: inside comment, last char was '*'
        ACCEPTED // q4: closed comment via '*/'
    }

    private static boolean isValidSymbol(char ch) {
        return ch == 'a' || ch == '*' || ch == '/';
    }

    public static boolean isValidComment(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        State currentState = State.START;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            // Reject any character outside the defined alphabet Σ = { 'a', '*', '/' }
            if (!isValidSymbol(ch)) {
                return false;
            }

            switch (currentState) {
                case START:
                    if (ch == '/') {
                        currentState = State.SEEN_SLASH;
                    } else {
                        return false;
                    }
                    break;

                case SEEN_SLASH:
                    if (ch == '*') {
                        currentState = State.BODY;
                    } else {
                        return false;
                    }
                    break;

                case BODY:
                    if (ch == '*') {
                        currentState = State.SEEN_STAR;
                    } else if (ch == 'a' || ch == '/') {
                        currentState = State.BODY;
                    }
                    break;

                case SEEN_STAR:
                    if (ch == '/') {
                        currentState = State.ACCEPTED;
                    } else if (ch == '*') {
                        currentState = State.SEEN_STAR;
                    } else if (ch == 'a') {
                        currentState = State.BODY;
                    }
                    break;

                case ACCEPTED:
                    // Any character after a complete comment causes rejection
                    return false;
            }
        }

        return currentState == State.ACCEPTED;
    }

    public static void main(String[] args) {
        String[] testCases = {
                "/*a*/",
                "/**/",
                "/***/",
                "/*aaa*aaa*/",
                "/*a/a*/",
                "/**",
                "/**/a/*aa*/",
                "aaa/**/aa",
                "/*/",
                "/**a/",
                "//aaaa",
                "/*extra*/"
        };

        for (String test : testCases) {
            System.out.printf("%-12s -> %s%n", "\"" + test + "\"", isValidComment(test) ? "ACCEPTED" : "REJECTED");
        }
    }
}