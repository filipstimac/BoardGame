package com.gamesRevolted.boardGame.modifier;

import java.util.Objects;

public abstract class Modifier {
  int value;

  public Modifier() {
  }

  public Modifier(int value) {
    this.value = value;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Modifier))
      return false;
    Modifier modifier = (Modifier) o;
    return value == modifier.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
