package lab.snake;

import java.util.List;

public enum Scenario {
    NORMAL(List.of(new Cell(3,2), new Cell(2,2), new Cell(1,2)), Direction.RIGHT, new Cell(6,2)),
    WALL(List.of(new Cell(6,2), new Cell(5,2), new Cell(4,2)), Direction.RIGHT, new Cell(0,0)),
    LOOP(List.of(new Cell(2,2), new Cell(2,3), new Cell(1,3), new Cell(1,2)), Direction.UP, new Cell(6,4)),
    PICNIC(List.of(new Cell(3,2), new Cell(2,2), new Cell(1,2)), Direction.RIGHT, new Cell(4,2));
    public final List<Cell> snake;
    public final Direction direction;
    public final Cell food;
    Scenario(List<Cell> snake, Direction direction, Cell food) {
        this.snake = snake; this.direction = direction; this.food = food;
    }
}
