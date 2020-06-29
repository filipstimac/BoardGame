package com.gamesRevolted.boardGame.character;

import java.util.List;
import java.util.Objects;

import com.gamesRevolted.boardGame.exception.InvalidAttackException;
import com.gamesRevolted.boardGame.modifier.Modifier;

public abstract class Character {
  protected String entityId;
  protected int health;
  protected int attack;
  protected List<Modifier> modifiers;
  protected boolean retaliate;

  public Character() {
  }

  public Character(String entityId, int health, int attack, List<Modifier> modifiers, boolean retaliate) {
    this.entityId = entityId;
    this.health = health;
    this.attack = attack;
    this.modifiers = modifiers;
    this.retaliate = retaliate;
  }

  public void attack(Character character) throws InvalidAttackException {
    if (this.equals(character)) {
      throw new InvalidAttackException("Character can't attack itself.");
    } else if (this.getHealth() < 1) {
      throw new InvalidAttackException("Character " + this.getEntityId() + " can't attack because his health is 0.");
    } else if (character.getHealth() < 1) {
      throw new InvalidAttackException(
        "Character " + character.getEntityId() + " can't be attacked because his health is 0.");
    }
    this.dealDamage(character);

    if (character.shouldRetaliate()) {
      character.dealDamage(this);
    }
  }

  private void dealDamage(Character character) {
    int totalDamage = attack;
    totalDamage += character.getModifiers().stream().mapToInt(Modifier::getIncomingDamageModifier).sum();
    character.setHealth(Math.max(character.getHealth() - totalDamage, 0));
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public List<Modifier> getModifiers() {
    return modifiers;
  }

  public void setModifiers(List<Modifier> modifiers) {
    this.modifiers = modifiers;
  }

  public boolean shouldRetaliate() {
    return retaliate;
  }

  public void setRetaliate(boolean retaliate) {
    this.retaliate = retaliate;
  }

  @Override
  public String toString() {
    return "entityId='" + entityId + '\'' + ", health=" + health + ", attack=" + attack + ", modifiers=" + modifiers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Character))
      return false;
    Character character = (Character) o;
    return health == character.health && attack == character.attack && retaliate == character.retaliate && entityId
      .equals(character.entityId) && modifiers.equals(character.modifiers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entityId, health, attack, modifiers, retaliate);
  }
}
