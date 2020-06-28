package com.gamesRevolted.boardGame.board;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gamesRevolted.boardGame.character.Character;
import com.gamesRevolted.boardGame.serialization.CharacterDeserializer;
import com.gamesRevolted.boardGame.serialization.CharacterSerializer;

public class BoardJsonHandler {

  private final String BOARD_PATH = "src/main/resources/board.json";

  private ObjectMapper objectMapper;

  public BoardJsonHandler() {
    this.objectMapper = new ObjectMapper();
    SimpleModule deserializerModule = new SimpleModule();
    deserializerModule.addDeserializer(Character.class, new CharacterDeserializer());
    objectMapper.registerModule(deserializerModule);
    SimpleModule serializerModule = new SimpleModule();
    serializerModule.addSerializer(Character.class, new CharacterSerializer());
    objectMapper.registerModule(serializerModule);
  }

  public Board parseBoard() {
    List<Character> charactersFiltered = null;
    try {
      List<Character> characters = objectMapper.readValue(new File(BOARD_PATH), new TypeReference<List<Character>>(){});
      charactersFiltered = characters.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read board JSON file.");
    }

    return new Board(charactersFiltered);
  }

  public void saveBoard(Collection characters) {
    ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    try {
      objectWriter.writeValue(new File("src/main/resources/boardOut.json"), characters);
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to save board JSON file.");
    }
  }
}
