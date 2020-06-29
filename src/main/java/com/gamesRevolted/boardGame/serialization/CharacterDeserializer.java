package com.gamesRevolted.boardGame.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gamesRevolted.boardGame.character.Avatar;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.character.CharacterFactory;
import com.gamesRevolted.boardGame.exception.InvalidTypeException;
import com.gamesRevolted.boardGame.modifier.Modifier;
import com.gamesRevolted.boardGame.modifier.ModifierFactory;

public class CharacterDeserializer extends StdDeserializer<Character> {
  private CharacterFactory characterFactory;
  private ModifierFactory modifierFactory;

  public CharacterDeserializer() {
    this(null);
    this.characterFactory = new CharacterFactory();
    this.modifierFactory = new ModifierFactory();
  }

  public CharacterDeserializer(Class<?> vc) {
    super(vc);
    this.characterFactory = new CharacterFactory();
    this.modifierFactory = new ModifierFactory();
  }

  public Character deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
    throws IOException {
    Character character = null;
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    if (!node.has("entityId") || !node.has("health") || !node.has("attack") || !node.has("modifiers")) {
      throw new IOException("JSON file is invalid.");
    }

    int entityType = (Integer) node.get("entityType").numberValue();
    try {
      character = characterFactory.createCharacter(entityType);
      String characterId = node.get("entityId").asText();
      character.setEntityId(characterId);
      character.setHealth((Integer) node.get("health").numberValue());
      character.setAttack((Integer) node.get("attack").numberValue());
      List<Modifier> modifiers = new ArrayList<>();
      if (node.has("modifiers")) {
        Iterator<JsonNode> modifiersIterator = node.get("modifiers").elements();
        while (modifiersIterator.hasNext()) {
          Modifier modifier = deserializeModifier(modifiersIterator.next(), characterId);
          if (modifier != null) {
            modifiers.add(modifier);
          }
        }
      }
      character.setModifiers(modifiers);
      if (character instanceof Avatar && node.has("attackReady")) {
        ((Avatar) character).setAttackReady(node.get("attackReady").asBoolean());
      } else if (character instanceof Avatar && !node.has("attackReady")) {
        throw new IOException("JSON file is invalid.");
      }
    }
    catch (InvalidTypeException e) {
      System.out.println("Character with ID " + node.get("entityId").asText() + " not serialized due to invalid entityType");
    }

    return character;
  }

  private Modifier deserializeModifier(JsonNode modifierJson, String characterId) {
    Modifier modifier = null;

    try {
      modifier = modifierFactory.createModifier((Integer) modifierJson.get("modifierType").numberValue());
      modifier.setValue((Integer) modifierJson.get("value").numberValue());
    }
    catch (InvalidTypeException e) {
      System.out.println("Modifier for character with ID " + characterId + " not serialized due to invalid modifierType");
    }

    return modifier;
  }
}
