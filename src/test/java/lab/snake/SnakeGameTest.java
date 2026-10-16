package lab.snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeGameTest {
    private static void assertConnectedTrail(SnakeGame g) {
        var body = g.body();
        for (int i = 1; i < body.size(); i++) {
            Cell a = body.get(i - 1), b = body.get(i);
            int dx = Math.abs(a.x() - b.x()), dy = Math.abs(a.y() - b.y());
            assertTrue(dx + dy == 1, "body segments not adjacent: " + a + " -> " + b);
        }
    }

    @Test void normalMovePreservesLength() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);g.tick(Direction.RIGHT);
        assertEquals(new Cell(4,2),g.head());assertEquals(3,g.body().size());assertEquals(SnakeGame.Status.RUNNING,g.status());
        assertConnectedTrail(g);
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
    @Test void bottomWallEndsRound() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);
        g.tick(Direction.DOWN);g.tick(Direction.DOWN);g.tick(Direction.DOWN);g.tick(Direction.DOWN);
        assertEquals(SnakeGame.Status.WALL,g.status());assertEquals(new Cell(3,5),g.head());
    }
    @Test void leftWallEndsRound() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);
        g.tick(Direction.UP);g.tick(Direction.LEFT);g.tick(Direction.LEFT);g.tick(Direction.LEFT);
        assertEquals(SnakeGame.Status.WALL,g.status());assertEquals(new Cell(0,1),g.head());
    }
    @Test void endedRoundDoesNotMove() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);
        g.tick(Direction.UP);g.tick(Direction.UP);g.tick(Direction.UP);Cell head=g.head();
        g.tick(Direction.RIGHT);assertEquals(head,g.head());assertEquals(SnakeGame.Status.WALL,g.status());
    }
    @Test void restartRestoresStartingBody() {
        SnakeGame g=new SnakeGame(Scenario.NORMAL);g.tick(Direction.RIGHT);g.reset();
        assertEquals(Scenario.NORMAL.snake,g.body());assertEquals(Scenario.NORMAL.food,g.food());
        assertEquals(Scenario.NORMAL.direction,g.direction());assertEquals(SnakeGame.Status.RUNNING,g.status());assertEquals(0,g.score());
    }
    @Test void loopMoveIntoOldTailIsLegal() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);g.tick(Direction.DOWN);
        assertEquals(new Cell(2,3),g.head());assertEquals(SnakeGame.Status.RUNNING,g.status());assertEquals(4,g.body().size());
        assertConnectedTrail(g);
    }
    @Test void loopTailFollowsTrailAndTurns() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);g.tick(Direction.RIGHT);
        assertEquals(new Cell(3,2),g.head());
        assertEquals(java.util.List.of(new Cell(3,2),new Cell(2,2),new Cell(1,2),new Cell(1,3)),g.body());
        assertEquals(SnakeGame.Status.RUNNING,g.status());assertEquals(4,g.body().size());
        assertConnectedTrail(g);
    }
    @Test void loopReverseIsIgnored() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);g.tick(Direction.LEFT);
        assertEquals(new Cell(3,2),g.head());assertEquals(Direction.RIGHT,g.direction());assertEquals(SnakeGame.Status.RUNNING,g.status());
    }
    @Test void loopUpMoveUnaffected() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);g.tick(Direction.UP);
        assertEquals(new Cell(2,1),g.head());assertEquals(SnakeGame.Status.RUNNING,g.status());assertEquals(4,g.body().size());
        assertConnectedTrail(g);
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
    @Test void nonTailSelfCollisionEndsRoundAndKeepsBody() {
        SnakeGame g=new SnakeGame(Scenario.LOOP);
        String seq="SDAASDWA";
        for (int i=0;i<seq.length()-1;i++) { g.tick(Direction.fromKey(seq.charAt(i))); assertConnectedTrail(g); }
        int lenBefore=g.body().size();
        g.tick(Direction.fromKey(seq.charAt(seq.length()-1)));
        assertEquals(SnakeGame.Status.SELF,g.status());
        assertEquals(lenBefore,g.body().size());
        assertConnectedTrail(g);
    }
}