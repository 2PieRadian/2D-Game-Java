package levels;

public class Level {

    private int[][] levelData;

    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    public int getSpriteIndex(int x, int y) {
        // Returns the sprite index at tile coordinate (x, y).
        // Suppose we need the tile to render at x and y starting from (2,0), then we would need to look at (0, 2) inside our levelData.
        return levelData[y][x];
    }
}
