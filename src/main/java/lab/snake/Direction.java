package lab.snake;

public enum Direction {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);
    public final int dx, dy;
    Direction(int dx, int dy) { this.dx = dx; this.dy = dy; }
    public boolean opposite(Direction other) { return dx == -other.dx && dy == -other.dy; }
    public static Direction fromKey(char key) {
        return switch (Character.toUpperCase(key)) {
            case 'W' -> UP; case 'S' -> DOWN; case 'A' -> LEFT; case 'D' -> RIGHT;
            default -> throw new IllegalArgumentException("Use W, A, S or D");
        };
    }
}
