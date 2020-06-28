package com.gamesRevolted.boardGame;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.gamesRevolted.boardGame.board.Board;
import com.gamesRevolted.boardGame.board.BoardJsonHandler;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.exception.InvalidAttackException;

public class Game {
  private Map<String, Character> boardMap;
  private BoardJsonHandler boardJsonHandler;

  public Game() {
    this.boardMap = new HashMap<>();
    this.boardJsonHandler = new BoardJsonHandler();
  }

  public Map<String, Character> getBoardMap() {
    return boardMap;
  }

  public void setBoardMap(Map<String, Character> boardMap) {
    this.boardMap = boardMap;
  }

  public void start() {
    Board board = boardJsonHandler.parseBoard();
    boardMap = board.getCharacters().stream().collect(
      Collectors.toMap(Character::getEntityId, character -> character));
    System.out.println("Game initialized.");
    System.out.println(boardMap.values());
  }

  public void attack(String id1, String id2) {
    try {
      if (id1.equals(id2)) {
        System.out.println("Character can't attack itself");
      }
      boardMap.get(id1).attack(boardMap.get(id2));
      boardJsonHandler.saveBoard(boardMap.values());
      System.out.println(boardMap.values());
    }
    catch (InvalidAttackException e) {
      System.out.println(e.getMessage());
    }
  }
}
