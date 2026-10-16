package lab.snake;

import java.util.Scanner;

public class TerminalSnake {
    public static void main(String[] args) {
        Scenario scenario = Scenario.NORMAL; String replay = null;
        for (int i=0; i<args.length; i++) {
            switch (args[i]) {
                case "--scenario" -> scenario = Scenario.valueOf(args[++i].toUpperCase());
                case "--replay" -> replay = args[++i].toUpperCase();
                default -> throw new IllegalArgumentException("Unknown argument: " + args[i]);
            }
        }
        if (replay != null) { replay(scenario,replay); return; }
        Scanner in=new Scanner(System.in);
        System.out.println("TERMINAL SNAKE | 1 NORMAL  2 WALL  3 LOOP  4 PICNIC");
        System.out.print("Choose a starting position [1]: ");
        String choice=in.nextLine().trim();
        scenario=switch(choice) { case "2" -> Scenario.WALL; case "3" -> Scenario.LOOP; case "4" -> Scenario.PICNIC; default -> Scenario.NORMAL; };
        SnakeGame game=new SnakeGame(scenario);
        System.out.println("W/A/S/D + Enter: one move | Enter: continue | R: restart | Q: quit");
        System.out.println(game.render());
        while (in.hasNextLine()) {
            System.out.print("> ");String key=in.nextLine().trim().toUpperCase();
            if (key.equals("Q")) break;
            if (key.equals("R")) game.reset();
            else if (key.isEmpty()) game.tick(game.direction());
            else if (key.length()==1 && "WASD".contains(key)) game.tick(Direction.fromKey(key.charAt(0)));
            else { System.out.println("Use one direction, R or Q."); continue; }
            System.out.println(game.render());
            if (game.status()!=SnakeGame.Status.RUNNING) System.out.println("Round ended. R restarts; Q quits.");
        }
    }
    public static void replay(Scenario scenario, String keys) {
        SnakeGame game=new SnakeGame(scenario);
        System.out.println("scenario="+scenario+" replay="+keys);System.out.println(game.render());
        for (char key:keys.toCharArray()) {
            if (key=='R') game.reset(); else game.tick(Direction.fromKey(key));
            System.out.println("key="+key+" "+game.snapshot());
        }
    }
}
