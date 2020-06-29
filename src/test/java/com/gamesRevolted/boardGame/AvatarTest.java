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

public class AvatarTest {
  private Avatar avatar;

  @Mock
  public Creature creatureMock;

  @Rule
  public ExpectedException rule = ExpectedException.none();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAttackAttackNotReady() throws InvalidAttackException {
    avatar = new Avatar("e1", 2, 2, Collections.emptyList(), false);

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Avatar is not ready to attack.");

    avatar.attack(creatureMock);
  }

  @Test
  public void testAttackItself() throws InvalidAttackException {
    avatar = new Avatar("e1", 2, 2, Collections.emptyList(), true);

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Character can't attack itself.");

    avatar.attack(avatar);
  }

  @Test
  public void testAttacker0Health() throws InvalidAttackException {
    avatar = new Avatar("e1", 0, 2, Collections.emptyList(), true);

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Character e1 can't attack because his health is 0.");

    avatar.attack(creatureMock);
  }

  @Test
  public void testAttackTarget0Health() throws InvalidAttackException {
    avatar = new Avatar("e1", 2, 2, Collections.emptyList(), true);
    when(creatureMock.getHealth()).thenReturn(0);
    when(creatureMock.getEntityId()).thenReturn("e2");

    rule.expect(InvalidAttackException.class);
    rule.expectMessage("Character e2 can't be attacked because his health is 0.");

    avatar.attack(creatureMock);
  }

  @Test
  public void testAttackSuccessfulNoModifiers() throws InvalidAttackException {
    avatar = new Avatar("e1", 2, 2, Collections.emptyList(), true);
    Creature creature = new Creature("e1", 2, 2, Collections.emptyList());

    avatar.attack(creature);

    Assert.assertEquals(0, avatar.getHealth());
    Assert.assertFalse(avatar.isAttackReady());
    Assert.assertEquals(0, creature.getHealth());
  }
}
