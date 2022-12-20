
import java.io.BufferedReader;
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
        tiles = new Tile[10];
        mapTileNum = new int[tileWindow.maxWorldCol][tileWindow.maxWorldRow];
        getTileImage();
        loadMapData("assets/world01.txt");
    }

    public void getTileImage(){
        try{
            //Grass Texture
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("assets/grass.png"));
        
            //Water Texture
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("assets/water.png"));
            tiles[1].collision = true;
            //Path Texture\\
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("assets/path.png"));
            //----Wall Texture----\\
            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("assets/wall.png"));
            tiles[3].collision = true;
            //----Tree textures----\\
            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("assets/tree.png"));
            tiles[4].collision = true;

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("assets/water.png"));
            tiles[5].collision = true;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
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

