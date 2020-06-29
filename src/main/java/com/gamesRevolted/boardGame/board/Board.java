package com.gamesRevolted.boardGame.board;

import java.util.List;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Board))
      return false;
    Board board = (Board) o;
    return characters.equals(board.characters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(characters);
  }
}
