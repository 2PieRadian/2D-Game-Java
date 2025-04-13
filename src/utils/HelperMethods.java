package utils;

import java.awt.geom.Rectangle2D;
import java.util.Timer;

import static main.Game.TILES_SIZE;

public class HelperMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        return !IsTileSolid(x, y, levelData) && !IsTileSolid(x + width, y, levelData) && !IsTileSolid(x, y + height, levelData) && !IsTileSolid(x + width, y + height, levelData);
    }

    private static boolean IsTileSolid(float x, float y, int[][] levelData) {
        int xTile = (int) (x / TILES_SIZE);
        int yTile = (int) (y / TILES_SIZE);

        // Checking Boundary Conditions
        if (xTile < 0 || xTile >= levelData[0].length || yTile < 0 || yTile >= levelData.length)
            return true;

        // Make sure that the index of the Tile is valid
        int tileIndex = levelData[yTile][xTile];
        if (tileIndex >= 48 || tileIndex < 0) // Depends on the Level Sprite Image
            return true;

        // Not Solid Tiles
        return !(tileIndex == 11);
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        if (!IsTileSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData))
            if (!IsTileSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData))
                return false;
        return true;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        // Current tile the player is in
        int currentTile = (int) (hitbox.x / TILES_SIZE);

        // Snap to the Right Side
        if (xSpeed > 0) {
            int currentTileXPosition = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitbox.width);

            return currentTileXPosition + xOffset - 1;
        }

        return currentTile * TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / TILES_SIZE);

        // Falling (Touching ground)
        if (airSpeed > 0) {
            int tileYPosition = currentTile * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitbox.height);

            return tileYPosition + yOffset - 1;
        }
        return currentTile * TILES_SIZE;
    }
}