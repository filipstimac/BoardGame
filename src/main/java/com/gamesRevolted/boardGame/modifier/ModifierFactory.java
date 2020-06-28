package com.gamesRevolted.boardGame.modifier;

import com.gamesRevolted.boardGame.exception.InvalidTypeException;

public class ModifierFactory {
  public Modifier createModifier(int type) throws InvalidTypeException {
    Modifier modifier;

    if (type == 1) {
      modifier = new Armour();
    } else if (type == 2) {
      modifier = new Vulnerability();
    } else {
      throw new InvalidTypeException("Invalid modifier type.");
    }

    return modifier;
  }
}
