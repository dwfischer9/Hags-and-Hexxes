import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class TileManager {
    Window tileWindow;
    Tile[] tiles;
    int mapTileNum[] [];
    public TileManager(Window tileWindow){
        this.tileWindow = tileWindow;
        tiles = new Tile[10];
        mapTileNum = new int[Window.maxScreenCol][Window.maxScreenRow];
        getTileImage();
        loadMapData("assets/mapdata.txt");
    }

    public void getTileImage(){
        try{
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("assets/Grass..png"));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("assets/Water1...png"));
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
            while(col < Window.maxScreenCol && row < Window.maxScreenRow){
                String line = br.readLine();
                while(col < Window.maxScreenCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }if(col == Window.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        
        } catch (Exception e) {
            
        }
    }
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while(col < Window.maxScreenCol && row < Window.maxScreenRow){

            int tileNum = mapTileNum[col][row];
            if(tileWindow.isVisible() == true && tileWindow.getName() == "overWorldPanel"){
                g2.drawImage(tiles[tileNum].image,x,y,Window.tileSize,Window.tileSize,null);    
            }
            col ++;
            x+= Window.tileSize;
            if(col == Window.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += Window.tileSize;
            }
        }
    }

}

