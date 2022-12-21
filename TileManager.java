
import java.io.BufferedReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;


public class TileManager {
    Window tileWindow;
    Tile[] tiles;
    int mapTileNum[][];
    public TileManager(Window tileWindow){
        this.tileWindow = tileWindow;
        tiles = new Tile[20];
        mapTileNum = new int[tileWindow.maxWorldCol][tileWindow.maxWorldRow];
        getTileImage();
        loadMapData("assets/world01.txt");
    }

    public void getTileImage(){
        try{
            setup(0, "grass", false);
            setup(1, "grass1", false);

            setup(2, "path", false);

            setup(3, "wall", true);

            setup(4, "tree", true);
            setup(5, "tree", true);

            setup(6, "water", true);
            setup(7, "edgeofwater-left", true);
            setup(8, "water-corner-bottom-left",true);
            setup(9, "edgeofwater-right", true);
            setup(10, "water-corner-top-right", true);
            setup(11, "water-corner-top-left", true);
            setup(12, "water-corner-bottom-right",true);
            setup(13, "edgeofwater-top", true);
            setup(14, "edgeofwater-bottom", true);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * A helper method to pre-scale the images before the game loop begins, intended to improve performance by only loading images once.
     * @param index the number the tile will be represented by in map files.
     * @param imagePath the name of the tile, excluding the extension as all tiles in this program will be in PNG format.
     * @param collision True: The tile should be impassable by the player. False(default): The player can pass through the tile.
     * @throws IOException
     */
    public void setup(int index, String imagePath,boolean collision) throws IOException{
        UtilityTools uTool = new UtilityTools();
        tiles[index] = new Tile();
        BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/assets/" + imagePath + ".png"));
        tiles[index].image = uTool.scaleImage(img, Window.tileSize, Window.tileSize);
        tiles[index].collision = collision;
    }

/**
         * A method that is used to load map files. This currently only supports loading maps
         * that are 50x50 grids. This will need to be updated soon to support smaller or larger instance maps.
         * TODO: Add support for loading maps of any size as long as they are formatted properly)
     * 
     * @param filePath - The relative path to the map file.
     */
    public void loadMapData(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col < tileWindow.maxWorldCol && row < tileWindow.maxWorldRow){
                String line = br.readLine();
                while(col < tileWindow.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }if(col == tileWindow.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        
        } catch (Exception e) {
            
        }
    }
    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;
        
        while(worldCol < tileWindow.maxWorldCol && worldRow < tileWindow.maxWorldRow){

            int worldX = worldCol * Window.tileSize;
            int worldY = worldRow * Window.tileSize;
            int screenX = worldX - Window.player.worldX + Window.player.screenX;
            int screenY = worldY - Window.player.worldY + Window.player.screenY;

            int tileNum = mapTileNum[worldCol][worldRow];
            if(tileWindow.isVisible() == true && tileWindow.getName() == "overWorldPanel"){
                if(
                worldX + Window.tileSize > Window.player.worldX - Window.player.screenX && 
                worldX - Window.tileSize < Window.player.worldX + Window.player.screenX && 
                worldY + Window.tileSize > Window.player.worldY - Window.player.screenY && 
                worldY - Window.tileSize < Window.player.worldY + Window.player.screenY) // only render tiles in the camera view
                g2.drawImage(tiles[tileNum].image,screenX,screenY,Window.tileSize,Window.tileSize,null);    
            }
            worldCol ++;
            if(worldCol == tileWindow.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }

}

