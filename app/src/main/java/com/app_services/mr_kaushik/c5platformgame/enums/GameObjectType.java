package com.app_services.mr_kaushik.c5platformgame.enums;

import java.util.HashMap;
import java.util.Map;

public enum GameObjectType {

    // TILE TYPES
    SPACE('.'),
    GRASS('1'),
    SNOW('2'),
    BRICK('3'),
    COAL('4'),
    CONCRETE('5'),
    SCORCHED('6'),
    STONE('7'),

    // ACTIVE OBJECTS
    PLAYER('p'),
    EXTRA_LIFE('e'),
    TELEPORT('t'),
    MACHINE_GUN_UPGRADE('u'),
    COIN('c'),

    // ENEMIES
    GUARD('g'),
    DRONE('d'),
    FIRE('f'),

    // INACTIVE OBJECTS
    TREE('w'),
    TREE_2('x'),
    LAMP_POST('l'),
    STALACTITE('r'),
    STALAGMITE('s'),
    MINE_CART('m'),
    BOULDERS('b');

    //    public final char
    private char character;

    GameObjectType(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return this.character;
    }

    private static final Map<Character, GameObjectType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (GameObjectType objectType : GameObjectType.values()) {
            lookup.put(objectType.getCharacter(), objectType);
        }
    }

    //This method can be used for reverse lookup purpose
    public static GameObjectType get(Character character) {
        return lookup.get(character);
    }
}
