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
  private ObjectMapper objectMapper;
  private String boardPath;

  public BoardJsonHandler(String path) {
    this.boardPath = path;
    this.objectMapper = new ObjectMapper();
    SimpleModule deserializerModule = new SimpleModule();
    deserializerModule.addDeserializer(Character.class, new CharacterDeserializer());
    objectMapper.registerModule(deserializerModule);
    SimpleModule serializerModule = new SimpleModule();
    serializerModule.addSerializer(Character.class, new CharacterSerializer());
    objectMapper.registerModule(serializerModule);
  }

  public Board parseBoard() throws IOException {
    List<Character> charactersFiltered = null;
    List<Character> characters = objectMapper.readValue(new File(boardPath), new TypeReference<List<Character>>() {
    });
    charactersFiltered = characters.stream().filter(Objects::nonNull).collect(Collectors.toList());

    return new Board(charactersFiltered);
  }

  public void saveBoard(Collection<Character> characters) throws IOException {
    ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    //TODO use variable when done
    objectWriter.writeValue(new File("src/main/resources/boardOut.json"), characters);
  }
}
