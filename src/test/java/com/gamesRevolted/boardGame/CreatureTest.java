package com.gamesRevolted.boardGame;

import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gamesRevolted.boardGame.character.Avatar;
import com.gamesRevolted.boardGame.character.Creature;
import com.gamesRevolted.boardGame.exception.InvalidAttackException;
import com.gamesRevolted.boardGame.modifier.Armour;
import com.gamesRevolted.boardGame.modifier.Vulnerability;

public class CreatureTest {
  private Creature creature;

  @Mock
  public Avatar avatarMock;
  @Mock
  public Creature creatureMock;

  @Rule
  public ExpectedException rule = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAttackAttackAvatar() throws InvalidAttackException {
    creature = new Creature("e1", 2, 2, Collections.emptyList());

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Creature tried attacking avatar.");

    creature.attack(avatarMock);
  }

  @Test
  public void testAttackItself() throws InvalidAttackException {
    creature = new Creature("e1", 2, 2, Collections.emptyList());

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Character can't attack itself.");

    creature.attack(creature);
  }

  @Test
  public void testAttacker0Health() throws InvalidAttackException {
    creature = new Creature("e1", 0, 2, Collections.emptyList());

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Character e1 can't attack because his health is 0.");

    creature.attack(creatureMock);
  }

  @Test
  public void testAttackTarget0Health() throws InvalidAttackException {
    creature = new Creature("e1", 2, 2, Collections.emptyList());
    when(creatureMock.getHealth()).thenReturn(0);
    when(creatureMock.getEntityId()).thenReturn("e2");

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Character e2 can't be attacked because his health is 0.");

    creature.attack(creatureMock);
  }

  @Test
  public void testAttackSuccessfulModifiers() throws InvalidAttackException {
    creature = new Creature("e1", 2, 1, Collections.singletonList(new Armour(1)));
    Creature creature2 = new Creature("e2", 2, 1, Collections.singletonList(new Vulnerability(1)));

    creature.attack(creature2);

    Assert.assertEquals(0, creature2.getHealth());
    Assert.assertEquals(2, creature.getHealth());
  }
}
