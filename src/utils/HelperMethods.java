package utils;

import java.time.chrono.IsoChronology;

import static main.Game.TILES_SIZE;

public class HelperMethods {

    public static boolean CanMoveHere(float x, float y, int width, int height, int[][] levelData) {
        return !IsTileSolid(x, y, levelData) && !IsTileSolid(x + width, y, levelData) && !IsTileSolid(x, y + height, levelData) && !IsTileSolid(x + width, y + height, levelData);
    }

    private static boolean IsTileSolid(float x, float y, int[][] levelData) {
        int xTile = (int) x / TILES_SIZE;
        int yTile = (int) y / TILES_SIZE;

        // Checking Boundary Conditions
        if (xTile < 0 || xTile >= levelData[0].length || yTile < 0 || yTile >= levelData.length) {
            return true;
        }

        // Make sure that the index of the Tile is valid
        int tileIndex = levelData[yTile][xTile];
        if (tileIndex >= 48 || tileIndex < 0)
            return true;

        // Walkable Tiles
        return !(tileIndex == 11);
    }

}