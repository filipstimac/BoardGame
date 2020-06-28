package com.gamesRevolted.boardGame.character;

import com.gamesRevolted.boardGame.exception.InvalidAttackException;

public class Creature extends Character {
  public void attack(Character character) throws InvalidAttackException {
    if (character instanceof Avatar) {
      throw new InvalidAttackException("Creature tried attacking avatar.");
    } else {
      attackRetaliateAndLog(character);
    }
  }

  @Override
  public String toString() {
    return "Creature{" + super.toString() + '}';
  }
}
