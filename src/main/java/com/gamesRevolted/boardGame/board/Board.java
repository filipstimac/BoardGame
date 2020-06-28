package com.gamesRevolted.boardGame.board;

import java.util.List;

import com.gamesRevolted.boardGame.character.Character;

public class Board {
  private List<Character> characters;

  public Board(List<Character> characters) {
    this.characters = characters;
  }

  public List<Character> getCharacters() {
    return characters;
  }

  public void setCharacters(List<Character> characters) {
    this.characters = characters;
  }

  @Override
  public String toString() {
    return "Board{" + "characters=" + characters + '}';
  }
}
