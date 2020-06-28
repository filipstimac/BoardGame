package com.gamesRevolted.boardGame.character;

import com.gamesRevolted.boardGame.exception.InvalidTypeException;

public class CharacterFactory {
  public Character createCharacter(int type) throws InvalidTypeException {
    Character character;

    if (type == 1) {
      character = new Creature();
    } else if (type == 2) {
      character = new Avatar();
    } else {
      throw new InvalidTypeException("Invalid character type");
    }

    return character;
  }
}
