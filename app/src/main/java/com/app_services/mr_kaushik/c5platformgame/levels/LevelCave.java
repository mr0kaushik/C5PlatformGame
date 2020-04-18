package com.app_services.mr_kaushik.c5platformgame.levels;

import com.app_services.mr_kaushik.c5platformgame.backgrounds.BackgroundData;
import com.app_services.mr_kaushik.c5platformgame.level_utils.LevelData;

import java.util.ArrayList;

public class LevelCave extends LevelData {

    public LevelCave() {
        tiles = new ArrayList<>();
        this.tiles.add("p................11111111............");
        this.tiles.add("11111................................");
        this.tiles.add(".....111.........11..................");
        this.tiles.add("w...x................................");
        this.tiles.add(".........l...r....s.............1111.");
        this.tiles.add(".....................m...............");
        this.tiles.add("..........................b..........");
        this.tiles.add("1222233334444455556666777111111ffff..");

        backgroundDataList = new ArrayList<>();
        this.backgroundDataList.add(new BackgroundData("skyline",
                true, -1, 3, 18, 10, 15));

        this.backgroundDataList.add(new BackgroundData("grass",
                true, 1, 20, 24, 24, 4));
    }
}
