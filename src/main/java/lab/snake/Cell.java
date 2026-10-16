package lab.snake;

public record Cell(int x, int y) {
    public Cell move(Direction d) { return new Cell(x + d.dx, y + d.dy); }
}
