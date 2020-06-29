package com.gamesRevolted.boardGame;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.gamesRevolted.boardGame.board.Board;
import com.gamesRevolted.boardGame.board.BoardJsonHandler;
import com.gamesRevolted.boardGame.character.Avatar;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.exception.InvalidAttackException;

public class Game {
  private Map<String, Character> boardMap;
  private BoardJsonHandler boardJsonHandler;

  public Game(String path) {
    this.boardMap = new HashMap<>();
    this.boardJsonHandler = new BoardJsonHandler(path);
  }

  public Map<String, Character> getBoardMap() {
    return boardMap;
  }

  public void setBoardMap(Map<String, Character> boardMap) {
    this.boardMap = boardMap;
  }

  public void start() {
    Board board = null;
    try {
      board = boardJsonHandler.parseBoard();
      boardMap = board.getCharacters().stream()
        .collect(Collectors.toMap(Character::getEntityId, character -> character));
      System.out.println("Game initialized.");
      System.out.println(boardMap.values());
    }
    catch (IOException e) {
      System.out.println("Not able to read JSON file.");
    }
  }

  public void attack(String id1, String id2) {
    try {
      Character character1 = boardMap.get(id1);
      Character character2 = boardMap.get(id2);
      character1.attack(character2);
      boardJsonHandler.saveBoard(boardMap.values());
      System.out.println(
        "EntityAttackedMessage: " + character1.getEntityId() + " " + character2.getEntityId() + " " + character1
          .getAttack());
      //TODO remove when done
      System.out.println(boardMap.values());
    }
    catch (InvalidAttackException e) {
      System.out.println(e.getMessage());
    }
    catch (IOException e) {
      System.out.println("Not able to save board to JSON file.");
    }
  }

  public void endRound() {
    boardMap.values().stream().filter(character -> character instanceof Avatar)
      .forEach(character -> ((Avatar) character).setAttackReady(true));
    System.out.println("Round ended, new round started.");
  }
}
