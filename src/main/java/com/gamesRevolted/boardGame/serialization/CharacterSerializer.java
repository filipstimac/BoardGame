package com.gamesRevolted.boardGame.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gamesRevolted.boardGame.character.Avatar;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.modifier.Armour;
import com.gamesRevolted.boardGame.modifier.Modifier;

public class CharacterSerializer extends StdSerializer<Character> {

  public CharacterSerializer() {
    this(null);
  }

  public CharacterSerializer(Class<?> vc) {
    super((Class<Character>) vc);
  }

  @Override
  public void serialize(Character value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeStartObject();
    gen.writeStringField("entityId", value.getEntityId());
    gen.writeNumberField("health", value.getHealth());
    gen.writeNumberField("attack", value.getAttack());
    if (value instanceof Avatar) {
      gen.writeBooleanField("attackReady", ((Avatar) value).isAttackReady());
      gen.writeNumberField("entityType", 2);
    } else {
      gen.writeNumberField("entityType", 1);
    }
    gen.writeArrayFieldStart("modifiers");
    for (Modifier modifier : value.getModifiers()) {
      gen.writeStartObject();
      if (modifier instanceof Armour) {
        gen.writeNumberField("modifierType", 1);
      } else {
        gen.writeNumberField("modifierType", 2);
      }
      gen.writeNumberField("value", modifier.getValue());
      gen.writeEndObject();
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}
