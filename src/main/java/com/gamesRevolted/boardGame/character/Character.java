package com.gamesRevolted.boardGame.character;

import java.util.List;

import com.gamesRevolted.boardGame.exception.InvalidAttackException;
import com.gamesRevolted.boardGame.modifier.Modifier;

public abstract class Character {
  private String entityId;
  private int health;
  private int attack;
  private List<Modifier> modifiers;

  public abstract void attack(Character character) throws InvalidAttackException;

  public boolean inflictDamageShouldRetaliate(Character character) {
    reduceHealthForTotalDamage(character);

    return this instanceof Creature;
  }

  protected void reduceHealthForTotalDamage(Character character) {
    int totalDamage = character.getAttack();
    totalDamage += getModifiers().stream().mapToInt(Modifier::getIncomingDamageModifier).sum();
    int resultingHealth = getHealth() - totalDamage;
    setHealth(resultingHealth);
  }

  protected void attackRetaliateAndLog(Character character) {
    boolean isRetaliated = character.inflictDamageShouldRetaliate(this);
    if (isRetaliated) {
      reduceHealthForTotalDamage(character);
    }
    System.out.println("EntityAttackedMessage: " + this.getEntityId() + " " + character.getEntityId() + " " + this.getAttack());
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

  public void addModifier(Modifier modifier) {
    this.modifiers.add(modifier);
  }

  @Override
  public String toString() {
    return "entityId='" + entityId + '\'' + ", health=" + health + ", attack=" + attack + ", modifiers="
      + modifiers;
  }
}
