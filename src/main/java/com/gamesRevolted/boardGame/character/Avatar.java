package com.gamesRevolted.boardGame.character;

import com.gamesRevolted.boardGame.exception.InvalidAttackException;

public class Avatar extends Character {
  private boolean attackReady;

  public void attack(Character character) throws InvalidAttackException {
    if (!attackReady) {
      throw new InvalidAttackException("Avatar is not ready to attack.");
    } else {
      attackReady = false;
      attackRetaliateAndLog(character);
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
}
