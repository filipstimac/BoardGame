package com.gamesRevolted.boardGame.modifier;

public abstract class Modifier {
  int value;

  public abstract int getIncomingDamageModifier();

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "value=" + value;
  }
}
