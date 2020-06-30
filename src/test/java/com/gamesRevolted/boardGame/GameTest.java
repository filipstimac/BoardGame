package com.gamesRevolted.boardGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gamesRevolted.boardGame.board.Board;
import com.gamesRevolted.boardGame.board.BoardJsonHandler;
import com.gamesRevolted.boardGame.character.Avatar;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.character.Creature;

public class GameTest {
  @Mock
  public BoardJsonHandler boardJsonHandler;

  @InjectMocks
  private Game game = new Game("mocked");

  private Avatar avatar;
  private Creature creature;
  private Board board;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    avatar = new Avatar("e1", 2, 2, Collections.emptyList(), true);
    creature = new Creature("e2", 2, 2, Collections.emptyList());
    board = new Board(new ArrayList<>(Arrays.asList(avatar, creature)));
  }

  @Test
  public void testStart() throws IOException {
    when(boardJsonHandler.parseBoard()).thenReturn(board);

    game.start();

    assertEquals(board.getCharacters(), new ArrayList<>(game.getBoardMap().values()));
  }

  @Test
  public void testAttack() throws IOException {
    game.setBoardMap(
      board.getCharacters().stream().collect(Collectors.toMap(Character::getEntityId, character -> character)));
    doNothing().when(boardJsonHandler).saveBoard(any(Collection.class));

    game.attack("e1", "e2");

    verify(boardJsonHandler).saveBoard(any(Collection.class));
    assertEquals(0, avatar.getHealth());
    assertEquals(0, creature.getHealth());
  }

  @Test
  public void testEndRound() {
    game.endRound();

    assertTrue(avatar.isAttackReady());
  }
}
