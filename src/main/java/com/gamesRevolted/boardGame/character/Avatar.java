package com.gamesRevolted.boardGame.character;

import java.util.List;
import java.util.Objects;

import com.gamesRevolted.boardGame.exception.InvalidAttackException;
import com.gamesRevolted.boardGame.modifier.Modifier;

public class Avatar extends Character {
  private boolean attackReady;

  public Avatar() {
  }

  public Avatar(String entityId, int health, int attack, List<Modifier> modifiers, boolean attackReady) {
    super(entityId, health, attack, modifiers, false);
    this.attackReady = attackReady;
  }

  @Override
  public void attack(Character character) throws InvalidAttackException {
    if (!attackReady) {
      throw new InvalidAttackException("Avatar is not ready to attack.");
    } else {
      attackReady = false;
      super.attack(character);
    }
  }

  public boolean isAttackReady() {
    return attackReady;
  }

  public void setAttackReady(boolean attackReady) {
    this.attackReady = attackReady;
  }

  @Override
  public String toString() {
    return "Avatar{" + super.toString() + ", attackReady=" + attackReady + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Avatar))
      return false;
    if (!super.equals(o))
      return false;
    Avatar avatar = (Avatar) o;
    return attackReady == avatar.attackReady;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), attackReady);
  }
}
