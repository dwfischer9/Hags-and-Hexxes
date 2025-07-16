import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

    public static int MAXWORLDCOL = 50;
    public static  int MAXWORLDROW = 50;
    Tile[] tiles;
    String[] tileNames = { "grass", "grass1", "path", "wall", "tree", "tree", "water", "edgeofwater-left",
            "water-corner-bottom-left", "edgeofwater-right", "water-corner-top-right", "water-corner-top-left",
            "water-corner-bottom-right", "edgeofwater-top", "edgeofwater-bottom" }; // tilenames and tile collisions
                                                                                    // must have the same length! to
                                                                                    // create a new tile, enter the name
                                                                                    // in tilenames and the collision in
                                                                                    // tilecollisions
    boolean[] tileCollisions = { false, false, false, true, true, true, true, true, true, true, true, true, true, true,
            true };
    int mapTileNum[][];
    public final int SCREEN_X = Window.SCREENWIDTH / 2 - Tile.TILESIZE / 2;
    public final int SCREEN_Y = Window.SCREENHEIGHT / 2 - Tile.TILESIZE / 2;
    public Entity player;
    public TileManager(Window window) {
        tiles = new Tile[20];
        this.player = Game.getInstance().getPlayer();
        mapTileNum = new int[MAXWORLDCOL][MAXWORLDROW];
        initializeTileImages();
        initializeMapData();
    }

    private void initializeTileImages() {
        if (tileNames.length != tileCollisions.length) {
            System.out.println("Error: tileNames and tileCollisions are not the same length");
            System.out.println(tileNames.length);
            System.out.println(tileCollisions.length);
        }
        try {
            for (int i = 0; i < tileNames.length; i++) {
                setup(i, tileNames[i], tileCollisions[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void getTileImage() {
        initializeTileImages();
    }

    /**
     * A helper method to pre-scale the images before the game loop begins, intended
     * to improve performance by only loading images once.
     * 
     * @param index     the number the tile will be represented by in map files.
     * @param imagePath the name of the tile, excluding the extension as all tiles will be in PNG format.
     * @param collision True: The tile should be impassable by the player.
     *                  False(default): The player can pass through the tile.
     * @throws IOException
     */
    public void setup(int index, String imagePath, boolean collision) throws IOException {
        UtilityTools uTool = new UtilityTools();
        BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/assets/" + imagePath + ".png"));
        BufferedImage scaledImg = uTool.scaleImage(img, Tile.TILESIZE, Tile.TILESIZE);
        tiles[index] = new Tile(scaledImg, collision);
    }

    private void initializeMapData() {
        loadMapData("assets/world01.txt");
    }
    
    /**
     * A method that is used to load map files. This currently only supports loading
     * maps
     * that are 50x50 grids. This will need to be updated soon to support smaller or
     * larger instance maps.
     * TODO: Add support for loading maps of any size as long as they are formatted
     * properly)
     * 
     * @param filePath - The relative path to the map file.
     */
    public void loadMapData(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                int col = 0;
                
                int row = 0;
                while (col < MAXWORLDCOL && row < MAXWORLDROW) {
                    String line = br.readLine();
                    while (col < MAXWORLDCOL) {
                        String numbers[] = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                        col++;
                    }
                    if (col == MAXWORLDCOL) {
                        col = 0;
                        row++;
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < MAXWORLDCOL && worldRow < MAXWORLDROW) {

            int worldX = worldCol * Tile.TILESIZE;
            int worldY = worldRow * Tile.TILESIZE;
            int screenX = worldX - player.getWorldX() + SCREEN_X;
            int screenY = worldY - player.getWorldY() + SCREEN_Y;

            int tileNum = mapTileNum[worldCol][worldRow];
            if (Game.getInstance().getWindow().isVisible()) {
                if (worldX + Tile.TILESIZE > player.getWorldX() - SCREEN_X &&
                        worldX - Tile.TILESIZE < player.getWorldX() + SCREEN_X &&
                        worldY + Tile.TILESIZE > player.getWorldY() - SCREEN_Y &&
                        worldY - Tile.TILESIZE < player.getWorldY() + SCREEN_Y) // only render
                                                                                         // tiles
                    // in
                    // the camera view
                    g2.drawImage(tiles[tileNum].getImage(), screenX, screenY, Tile.TILESIZE, Tile.TILESIZE, null);
            }
            worldCol++;
            if (worldCol == MAXWORLDCOL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
