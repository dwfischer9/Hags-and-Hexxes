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
        window.obj[0].worldX = Window.tileSize * 20;
        window.obj[0].worldY = Window.tileSize * 20;
        window.obj[1] = new ObjectLockedDoor();
        window.obj[1].worldX = Window.tileSize *23;
        window.obj[1].worldY = Window.tileSize *25;
        System.out.println(window.obj[0]);
       

    }
}