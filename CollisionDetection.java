
/**
 * This class handles collisions between entities and {@link Item}s or
 * {@link Tile}s.
 * 
 */
public class CollisionDetection {
    Window window;

    public CollisionDetection(Window window) {
        this.window = window;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.hitBox.x;
        int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.worldY + entity.hitBox.y;
        int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;

        int entityLeftCol = entityLeftWorldX / Window.tileSize;
        int entityRightCol = entityRightWorldX / Window.tileSize;
        int entityTopRow = entityTopWorldY / Window.tileSize;
        int entityBottomRow = entityBottomWorldY / Window.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true;
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true;
                break;

        }

    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < window.items.length; i++) {
            if (window.items[i] != null) {
                // get solid area position for both entity and object
                entity.hitBox.x += entity.worldX;
                entity.hitBox.y += entity.worldY;

                window.items[i].HITBOX.x += window.items[i].worldX;
                window.items[i].HITBOX.y += window.items[i].worldY;

                switch (entity.direction) {
                    case "up":
                        entity.hitBox.y -= entity.speed;
                        if (entity.hitBox.intersects(window.items[i].HITBOX)) {
                            if (window.items[i].collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "down":
                        entity.hitBox.y += entity.speed;
                        if (entity.hitBox.intersects(window.items[i].HITBOX)) {
                            if (window.items[i].collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "left":
                        entity.hitBox.x -= entity.speed;
                        if (entity.hitBox.intersects(window.items[i].HITBOX)) {
                            if (window.items[i].collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "right":
                        entity.hitBox.x += entity.speed;
                        if (entity.hitBox.intersects(window.items[i].HITBOX)) {
                            if (window.items[i].collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                }
                entity.hitBox.x = entity.hitBoxDefeaultX;
                entity.hitBox.y = entity.hitBoxDefeaultY;
                window.items[i].HITBOX.x = window.items[i].HITBOXDEFAULTX;
                window.items[i].HITBOX.y = window.items[i].HITBOXDEFAULTY;
            }

        }
        return index;
    }
}
