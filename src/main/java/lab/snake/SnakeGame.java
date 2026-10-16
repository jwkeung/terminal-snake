package lab.snake;

import java.util.*;

/** Turn-based engine; coordinates are zero-based. The head is the first cell. */
public class SnakeGame {
    public enum Status { RUNNING, WALL, SELF, WON }
    private final int width, height;
    private final Scenario scenario;
    private final Deque<Cell> snake = new ArrayDeque<>();
    private Direction direction;
    private Cell food;
    private int score;
    private Status status;
    private Random random;

    public SnakeGame(Scenario scenario) { this(8, 6, scenario); }
    public SnakeGame(int width, int height, Scenario scenario) {
        if (width < 7 || height < 5) throw new IllegalArgumentException("Board must be at least 7 x 5");
        this.width = width; this.height = height; this.scenario = scenario;
        reset();
    }
    public void reset() {
        snake.clear(); snake.addAll(scenario.snake);
        direction = scenario.direction; food = scenario.food;
        status = Status.RUNNING; random = new Random(3383);
    }
    public void tick(Direction requested) {
        if (status != Status.RUNNING) return;
        if (!direction.opposite(requested)) direction = requested;
        Cell next = snake.getFirst().move(direction);
        if (next.x() < 0 || next.y() < 0 || next.y() >= height) {
            status = Status.WALL; return;
        }
        boolean growing = next.equals(food);
        if (snake.contains(next)) { status = Status.SELF; return; }
        snake.addFirst(next);
        if (growing) { score += 10; placeFood(); }
        else snake.removeLast();
    }
    private void placeFood() {
        List<Cell> free = new ArrayList<>();
        for (int y=0; y<height; y++) for (int x=0; x<width; x++) {
            Cell cell = new Cell(x,y);
            if (!snake.contains(cell)) free.add(cell);
        }
        if (free.isEmpty()) { status = Status.WON; food = null; }
        else food = free.get(random.nextInt(free.size()));
    }
    public String render() {
        StringBuilder out = new StringBuilder("+" + "-".repeat(width) + "+\n");
        for (int y=0; y<height; y++) {
            out.append('|');
            for (int x=0; x<width; x++) {
                Cell c=new Cell(x,y);
                out.append(c.equals(snake.getFirst()) ? 'H' : snake.contains(c) ? 'o' : c.equals(food) ? '*' : '.');
            }
            out.append("|\n");
        }
        return out.append("+" + "-".repeat(width) + "+\n").append(snapshot()).toString();
    }
    public String snapshot() {
        return "status="+status+" head="+head()+" length="+snake.size()+" score="+score+" food="+food;
    }
    public Cell head() { return snake.getFirst(); }
    public List<Cell> body() { return List.copyOf(snake); }
    public Direction direction() { return direction; }
    public Cell food() { return food; }
    public int score() { return score; }
    public Status status() { return status; }
    public int width() { return width; }
    public int height() { return height; }
}
