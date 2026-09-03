public class NFACommentChecker {

    private enum State {
        START,     // q0: initial state
        SEEN_SLASH,// q1: seen opening '/'
        BODY,      // q2: inside comment body (not immediately following '*')
        SEEN_STAR, // q3: inside comment, immediately saw '*'
        ACCEPTED,  // q4: successfully closed by '*/'
        ERROR      // invalid sequence / syntax error
    }

    public static boolean isValidComment(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        State currentState = State.START;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

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
                    } else {
                        // Stays in BODY for any character other than '*'
                        currentState = State.BODY;
                    }
                    break;

                case SEEN_STAR:
                    if (ch == '/') {
                        currentState = State.ACCEPTED;
                    } else if (ch == '*') {
                        // Consecutive '*' characters remain in SEEN_STAR
                        currentState = State.SEEN_STAR;
                    } else {
                        // Any other character returns to the main comment body
                        currentState = State.BODY;
                    }
                    break;

                case ACCEPTED:
                    // If any extra character appears after the comment has closed
                    return false;

                default:
                    return false;
            }
        }

        return currentState == State.ACCEPTED;
    }

    public static void main(String[] args) {
        String[] testCases = {
            "/**/",
            "/*a*/",
            "/*hello world*/",
            "/***/",
            "/****/",
            "/* a * b */",
            "/* / */",
            "/*",
            "*/",
            "/*/",
            "/*/*/",
            "/**/extra"
        };

        for (String test : testCases) {
            System.out.printf("%-18s -> %s%n", "\"" + test + "\"", isValidComment(test) ? "ACCEPTED" : "REJECTED");
        }
    }
}