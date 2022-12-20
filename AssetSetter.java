import java.io.IOException;

/**
 * AssetSetter
 */
public class AssetSetter {

    Window window;
    public AssetSetter(Window window) {
        this.window = window;
    }
    public void setObject() throws IOException{
        window.obj[0] = new ObjectChest();
        window.obj[0].worldX = window.tileSize * 23;
        window.obj[0].worldY = window.tileSize * 21;
        System.out.println(window.obj[0]);
       

    }
}