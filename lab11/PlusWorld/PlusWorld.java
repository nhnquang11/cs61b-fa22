package PlusWorld;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    private static boolean isValidCoordinate(TETile[][] world, int x, int y) {
        return 0 <= x && x <= world.length && 0 <= y && y <= world[0].length;
    }

    public static void addPlus(TETile[][] world, Point bottomRight, int size, TETile tile) {
        // bottom row
        for (int y = bottomRight.getY(); y < bottomRight.getY() + size; y++) {
            for (int x = bottomRight.getX() + size; x < bottomRight.getX() + 2 * size; x++) {
                if (isValidCoordinate(world, x, y)) {
                    world[x][y] = tile;
                }
            }
        }
        // middle row
        for (int y = bottomRight.getY() + size; y < bottomRight.getY() + 2 * size; y++) {
            for (int x = bottomRight.getX(); x < bottomRight.getX() + 3 * size; x++) {
                if (isValidCoordinate(world, x, y)) {
                    world[x][y] = tile;
                }
            }
        }
        // top row
        for (int y = bottomRight.getY() + 2 * size; y < bottomRight.getY() + 3 * size; y++) {
            for (int x = bottomRight.getX() + size; x < bottomRight.getX() + 2 * size; x++) {
                if (isValidCoordinate(world, x, y)) {
                    world[x][y] = tile;
                }
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        Point bottomRight = new Point(WIDTH/2 - 9, HEIGHT/2 - 9);
        addPlus(world, bottomRight, 6, Tileset.FLOWER);
        ter.renderFrame(world);
    }
}
