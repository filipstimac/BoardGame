package com.gamesRevolted.boardGame;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.character.Creature;
import com.gamesRevolted.boardGame.modifier.Armour;
import com.gamesRevolted.boardGame.serialization.CharacterDeserializer;

public class CharacterDeserializerTest {
  private ObjectMapper objectMapper = new ObjectMapper();

  @Rule
  public ExpectedException rule = ExpectedException.none();

  @Before
  public void setUp() {
    SimpleModule deserializerModule = new SimpleModule();
    deserializerModule.addDeserializer(Character.class, new CharacterDeserializer());
    objectMapper.registerModule(deserializerModule);
  }

  @Test
  public void testSerialization() throws IOException {
    Character character = new Creature("e1", 3, 1, Collections.singletonList(new Armour(1)));
    String boardPath = "src/test/resources/testBoard.json";
    List<Character> characters = objectMapper.readValue(new File(boardPath), new TypeReference<List<Character>>() {
    });

    Assert.assertEquals(Collections.singletonList(character), characters);
  }

  @Test
  public void testSerializationFailure() throws IOException {
    String boardPath = "src/test/resources/testBoardInvalid.json";
    rule.expect(IOException.class);
    rule.expectMessage("JSON file is invalid.");

    objectMapper.readValue(new File(boardPath), new TypeReference<List<Character>>() {
    });
  }
}
