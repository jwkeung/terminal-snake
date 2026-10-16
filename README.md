# Terminal Snake

## Play
Open TerminalSnake.java and click Run above main, or Run and Debug > Play Snake. The game uses VS Code's integrated terminal. Select 1 NORMAL, 2 WALL, 3 LOOP or 4 PICNIC. Type one W/A/S/D and Enter for one move; Enter continues, R restarts the same scenario and Q exits. Time does not advance while you type. H=head, o=body, *=food, .=empty. The board has 8 columns and 6 rows, starting at (0,0) in the top-left.

## Required rules
- Legal coordinates: 0 <= x < 8 and 0 <= y < 6. Trying to leave the board ends the round without moving the head outside.
- A reverse direction is ignored; the snake continues in its previous direction.
- Eating food grows the snake by one and adds 10 points. Food must occupy a free cell.
- On a move without food, the tail leaves its old cell. Moving into that old tail cell is legal. Moving into any other occupied cell ends the round.
- Restart restores that scenario's body, direction, food, RUNNING status and score 0.
- The same starting scenario and input sequence reproduce the same state.

## Replay and tests
Ask OpenCode to replay a starting position with an input sequence, e.g. LOOP with A. Run and Debug also supplies Replay LOOP A and Replay WALL DD. R is accepted within a replay. Use snapshots as report evidence. Run tests with VS Code Testing > Run All Tests, or ask OpenCode to run tests and generate JaCoCo coverage. Initial suite: 6 passing tests; it does not test every required rule. JaCoCo HTML: target/site/jacoco/index.html.

## Starting positions
NORMAL is ordinary play. WALL begins near the right boundary. LOOP is the classroom case: entering the old tail cell on a non-growing move. PICNIC places food ahead for the after-class restart exercise. They use the same controls and rules.

The starter deliberately contains defects. Existing passing tests are a baseline, not proof of correctness.