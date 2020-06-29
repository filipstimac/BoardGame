package com.gamesRevolted.boardGame.modifier;

public class Armour extends Modifier {
  public Armour() {
  }

  public Armour(int value) {
    super(value);
  }

  public int getIncomingDamageModifier() {
    return -this.value;
  }

  @Override
  public String toString() {
    return "Armour{" + super.toString() + '}';
  }
}
