package com.gamesRevolted.boardGame.character;

import java.util.List;

import com.gamesRevolted.boardGame.exception.InvalidAttackException;
import com.gamesRevolted.boardGame.modifier.Modifier;

public class Creature extends Character {
  public Creature() {
    super();
    this.retaliate = true;
  }

  public Creature(String entityId, int health, int attack, List<Modifier> modifiers) {
    super(entityId, health, attack, modifiers, true);
  }

  @Override
  public void attack(Character character) throws InvalidAttackException {
    if (character instanceof Avatar) {
      throw new InvalidAttackException("Creature tried attacking avatar.");
    } else {
      super.attack(character);
    }
  }

  @Override
  public String toString() {
    return "Creature{" + super.toString() + '}';
  }

}
