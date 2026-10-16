package lab.snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeGameTest {
    @Test void normalMovePreservesLength() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);g.tick(Direction.RIGHT);
        assertEquals(new Cell(4,2),g.head());assertEquals(3,g.body().size());assertEquals(SnakeGame.Status.RUNNING,g.status());
    }
    @Test void foodGrowsSnakeAndAddsTen() {
        SnakeGame g=new SnakeGame(Scenario.PICNIC);g.tick(Direction.RIGHT);
        assertEquals(4,g.body().size());assertEquals(10,g.score());assertFalse(g.body().contains(g.food()));
    }
    @Test void reverseDirectionIsIgnored() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);g.tick(Direction.LEFT);
        assertEquals(new Cell(4,2),g.head());assertEquals(Direction.RIGHT,g.direction());
    }
    @Test void topWallEndsRound() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);
        g.tick(Direction.UP);g.tick(Direction.UP);g.tick(Direction.UP);
        assertEquals(SnakeGame.Status.WALL,g.status());assertEquals(new Cell(3,0),g.head());
    }
    @Test void endedRoundDoesNotMove() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);
        g.tick(Direction.UP);g.tick(Direction.UP);g.tick(Direction.UP);Cell head=g.head();
        g.tick(Direction.RIGHT);assertEquals(head,g.head());assertEquals(SnakeGame.Status.WALL,g.status());
    }
    @Test void restartRestoresStartingBody() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);g.tick(Direction.RIGHT);g.reset();
        assertEquals(Scenario.NORMAL.snake,g.body());assertEquals(Scenario.NORMAL.food,g.food());assertEquals(SnakeGame.Status.RUNNING,g.status());
    }
    @Test void loopMoveIntoOldTailIsLegal() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);g.tick(Direction.LEFT);
        assertEquals(new Cell(1,2),g.head());assertEquals(SnakeGame.Status.RUNNING,g.status());assertEquals(4,g.body().size());
    }
    @Test void loopTailThenReverseIsIgnored() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);
        g.tick(Direction.LEFT);g.tick(Direction.RIGHT);
        assertEquals(new Cell(0,2),g.head());assertEquals(Direction.LEFT,g.direction());assertEquals(SnakeGame.Status.RUNNING,g.status());
    }
    @Test void loopUpMoveUnaffected() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);g.tick(Direction.UP);
        assertEquals(new Cell(2,1),g.head());assertEquals(SnakeGame.Status.RUNNING,g.status());assertEquals(4,g.body().size());
    }
    @Test void loopReverseDownIsIgnored() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);g.tick(Direction.DOWN);
        assertEquals(new Cell(2,1),g.head());assertEquals(Direction.UP,g.direction());assertEquals(SnakeGame.Status.RUNNING,g.status());
    }
    @Test void loopTailAfterLeftThenDownIsLegal() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);
        g.tick(Direction.LEFT);g.tick(Direction.DOWN);
        assertEquals(new Cell(1,3),g.head());assertEquals(SnakeGame.Status.RUNNING,g.status());
    }
    @Test void rightWallEndsRound() {
        SnakeGame g=new SnakeGame(Scenario.WALL);
        g.tick(Direction.RIGHT);g.tick(Direction.RIGHT);g.tick(Direction.RIGHT);
        assertEquals(SnakeGame.Status.WALL,g.status());assertEquals(new Cell(7,2),g.head());
    }
    @Test void wallMoveDoesNotLeaveBoard() {
        SnakeGame g=new SnakeGame(Scenario.WALL);
        g.tick(Direction.RIGHT);g.tick(Direction.RIGHT);g.tick(Direction.RIGHT);Cell head=g.head();
        g.tick(Direction.RIGHT);assertEquals(head,g.head());assertEquals(SnakeGame.Status.WALL,g.status());
    }
}
