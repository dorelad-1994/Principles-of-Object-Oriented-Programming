import DND.CharacterTypes;
import DND.CmdPrinter;
import DND.GameManager;
import DND.Level;
import DND.Players.Player;
import DND.Tiles.Tile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner myScanner = new Scanner(System.in);
            CharacterTypes types = new CharacterTypes();
            System.out.println("Select a player : ");
            types.printAllPlayers();
            int choice = myScanner.nextInt();
            while (!(choice > 0 && choice < 8)) {
                System.out.println("Invalid choice number , please select a player : ");
                choice = myScanner.nextInt();
            }
            Player chosenPlayer = types.getPlayerTile(choice);
            System.out.println("You have selected: " + "\n" + chosenPlayer.getName());
            //<AFTER CHOOSING A PLAYER WE LOAD ALL LEVELS OF GAME>
            List<String> levelFiles = Files.list(Paths.get(args[0])).sorted().map(Path::toString).collect(Collectors.toList());
            List<Level> levels = new ArrayList<>();
            for (String levelPath : levelFiles) {
                List<String> levelData = Files.readAllLines(Paths.get(levelPath));
                levels.add(new Level(levelData, chosenPlayer, types));
            }
            //<FINISHED LOADING LEVELS & CHARACTERS>


            //START THE GAME
            for (int i = 0; i < levels.size(); i++) {
                Level l = levels.get(i);
                l.getP().setPosition(l.getPlayerStartPosition());
                GameManager gameManager = new GameManager(l.getTiles(), l.getEnemies(), l.getP(), new CmdPrinter());
                System.out.println();
                System.out.println("Level " + (i + 1) + " Started");
                while (!gameManager.isDeadPlayer() && !gameManager.isAllEnemiesDead()) {
                    gameManager.printBoard();
                    gameManager.tick(myScanner.next().charAt(0));
                }
                if (gameManager.isDeadPlayer()) {
                    gameManager.printBoard();
                    chosenPlayer.describe();
                    System.out.println("Game Over.");
                    break;
                } else if (i < levels.size() - 1)
                    levels.get(i + 1).setP(l.getP());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
