package com.gamesRevolted.boardGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameSimulation {
  private static final String BOARD_PATH = "src/main/resources/board.json";

  public static void main(String[] args) {
    Game game = new Game(BOARD_PATH);

    game.start();

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String command = "";
    while (true) {
      try {
        command = reader.readLine();
        switch (command) {
          case "stop":
            System.exit(0);
          case "round":
            game.endRound();
            break;
          default:
            String[] splitCommand = command.split(" ");
            if (!game.getBoardMap().containsKey(splitCommand[0]) || !game.getBoardMap().containsKey(splitCommand[1])) {
              System.out.println("Invalid command.");
              continue;
            }
            game.attack(splitCommand[0], splitCommand[1]);
        }
      }
      catch (IOException e) {
        System.out.println("Not able to read console input.");
      }
    }
  }
}
