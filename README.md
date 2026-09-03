# C-Style Comment Recognizer

**Course:** Automata Theory & Formal Language (CS13a)

A Java implementation of a Finite Automaton designed to evaluate and recognize valid C-style block comments (`/* ... */`). This specific automaton restricts the valid input alphabet to $\Sigma = \{a, *, /\}$.

## State Transition Table

| State             | Input `a`   | Input `*`   | Input `/`   |
| :---------------- | :---------- | :---------- | :---------- |
| $\rightarrow q_0$ | $\emptyset$ | $\emptyset$ | $q_1$       |
| $q_1$             | $\emptyset$ | $q_2$       | $\emptyset$ |
| $q_2$             | $q_2$       | $q_3$       | $q_2$       |
| $q_3$             | $q_2$       | $q_3$       | $q_4$       |
| $* q_4$           | $\emptyset$ | $\emptyset$ | $\emptyset$ |

## State Definitions

- **$q_0$ (START):** Initial state. Expects the opening `/`.
- **$q_1$ (SEEN_SLASH):** Received `/`. Expects `*` to successfully open the comment block.
- **$q_2$ (BODY):** Inside the comment block. Loops on `a` and `/`.
- **$q_3$ (SEEN_STAR):** Encountered a `*` inside the block. Anticipates a closing `/`. Loops on consecutive `*` characters and returns to $q_2$ on `a`.
- **$q_4$ (ACCEPTED):** Successfully closed the comment sequence. Any trailing characters will cause the machine to reject the string.
