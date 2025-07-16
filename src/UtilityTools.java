import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UtilityTools {
    private final ArrayList<LightingNode> lightList = new ArrayList<>();

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
    
    public void addLight(LightingNode light) {
        if (lightList != null) {
            lightList.add(light);
        }
    }
    
    public ArrayList<LightingNode> getLightList() {
        return lightList;
    }

    public class LightingNode {
        private int worldX, worldY;
        private int witdth = Tile.TILESIZE;
        private int height = Tile.TILESIZE;
        private float lightLevel = 1.0f;
        private final Color lightColor = new Color(255, 255, 255, 180);

        /**
         * @param x   - world X coordinate
         * @param y   - world Y coordinate
         * @param lvl - the amount of squares to emit light.
         */
        public LightingNode(int x, int y, float lvl) {
            this.worldX = x * Tile.TILESIZE;
            this.worldY = y * Tile.TILESIZE;
            this.lightLevel = lvl;
        }

        /**
         * @param x      - world X coordinate
         * @param y      - world Y coordinate
         * @param lvl    - the amount of squares to emit light.
         * @param width  - the width of the light node
         * @param height - the height of the light node
         */
        public LightingNode(int x, int y, int width, int height, float lvl) {
            this.worldX = x * Tile.TILESIZE;
            this.worldY = y * Tile.TILESIZE;
            this.witdth = width;
            this.height = height;
            this.lightLevel = lvl;
        }

        public   void drawLights(Graphics2D g) {
            Graphics2D g2 = (Graphics2D) g.create();
            for (LightingNode light : lightList) {
                Rectangle Node = new Rectangle(light.worldX, light.worldY, light.witdth, light.height);
                g2.setStroke(new BasicStroke(3));
                g2.setColor(lightColor);
                g2.fill(Node);
            }
        }

        public int getWorldX() {
            return worldX;
        }

        public void setWorldX(int worldX) {
            this.worldX = worldX;
        }

        public int getWorldY() {
            return worldY;
        }

        public void setWorldY(int worldY) {
            this.worldY = worldY;
        }

        public float getLightLevel() {
            return lightLevel;
        }

        public void setLightLevel(float lightLevel) {
            this.lightLevel = lightLevel;
        }
    }
}
