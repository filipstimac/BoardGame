package com.gamesRevolted.boardGame;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.character.Creature;
import com.gamesRevolted.boardGame.modifier.Armour;
import com.gamesRevolted.boardGame.serialization.CharacterSerializer;

public class CharacterSerializerTest {
  private ObjectMapper objectMapper = new ObjectMapper();
  private final String boardJson = "[{\"entityId\":\"e1\",\"health\":3,\"attack\":1,\"entityType\":1,"
    + "\"modifiers\":[{\"modifierType\":1,\"value\":1}]}]";

  @Before
  public void setUp() {
    SimpleModule serializerModule = new SimpleModule();
    serializerModule.addSerializer(Character.class, new CharacterSerializer());
    objectMapper.registerModule(serializerModule);
  }

  @Test
  public void testSerialization() throws JsonProcessingException {
    Character character = new Creature("e1", 3, 1, Collections.singletonList(new Armour(1)));
    String result = objectMapper.writeValueAsString(Collections.singletonList(character));

    Assert.assertEquals(boardJson, result);
  }
}
