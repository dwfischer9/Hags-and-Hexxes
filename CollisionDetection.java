
/**
 * This class handles collisions between {@link Entities} and {@link Item}s or
 * {@link Tile}s.
 * 
 */
public class CollisionDetection {
    Window window;

    public CollisionDetection(Window window) {
        this.window = window;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.hitBox.x;
        int entityRightWorldX = entity.getWorldX() + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.getWorldY() + entity.hitBox.y;
        int entityBottomWorldY = entity.getWorldY() + entity.hitBox.y + entity.hitBox.height;

        int entityLeftCol = entityLeftWorldX / Window.tileSize;
        int entityRightCol = entityRightWorldX / Window.tileSize;
        int entityTopRow = entityTopWorldY / Window.tileSize;
        int entityBottomRow = entityBottomWorldY / Window.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true;
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / Window.tileSize;
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
                entity.hitBox.x += entity.getWorldX();
                entity.hitBox.y += entity.getWorldY();

                window.items[i].HITBOX.x += window.items[i].worldX;
                window.items[i].HITBOX.y += window.items[i].worldY;

                switch (entity.direction) {
                    case "up":
                        entity.hitBox.y -= entity.getSpeed();
                        if (entity.hitBox.intersects(window.items[i].HITBOX)) {
                            if (window.items[i].collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "down":
                        entity.hitBox.y += entity.getSpeed();
                        if (entity.hitBox.intersects(window.items[i].HITBOX)) {
                            if (window.items[i].collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "left":
                        entity.hitBox.x -= entity.getSpeed();
                        if (entity.hitBox.intersects(window.items[i].HITBOX)) {
                            if (window.items[i].collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "right":
                        entity.hitBox.x += entity.getSpeed();
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

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // get solid area position for both entity and object
                entity.hitBox.x += entity.getWorldX();
                entity.hitBox.y += entity.getWorldY();

                target[i].hitBox.x += target[i].getWorldX();
                target[i].hitBox.y += target[i].getWorldY();
                switch (entity.direction) {
                    case "up":

                        entity.hitBox.y -= entity.getSpeed();
                        if (entity.hitBox.intersects(target[i].hitBox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":

                        entity.hitBox.y += entity.getSpeed();
                        if (entity.hitBox.intersects(target[i].hitBox)) {
                            index = i;
                            entity.collisionOn = true;

                        }
                        break;
                    case "left":
                        entity.hitBox.x -= entity.getSpeed();
                        if (entity.hitBox.intersects(target[i].hitBox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.hitBox.x += entity.getSpeed();
                        if (entity.hitBox.intersects(target[i].hitBox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                entity.hitBox.x = entity.hitBoxDefeaultX;
                entity.hitBox.y = entity.hitBoxDefeaultY;
                target[i].hitBox.x = target[i].hitBoxDefeaultX;
                target[i].hitBox.y = target[i].hitBoxDefeaultX;
            }
        }

        return index;
    }

    public int checkAttackEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // get solid area position for both entity and object
                entity.attackArea.x += entity.getWorldX();
                entity.attackArea.y += entity.getWorldY();

                target[i].hitBox.x += target[i].getWorldX();
                target[i].hitBox.y += target[i].getWorldY();
                switch (entity.direction) {
                    case "up":

                        entity.attackArea.y -= entity.getSpeed();
                        if (entity.attackArea.intersects(target[i].hitBox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":

                        entity.attackArea.y += entity.getSpeed();
                        if (entity.attackArea.intersects(target[i].hitBox)) {
                            index = i;
                            entity.collisionOn = true;

                        }
                        break;
                    case "left":
                        entity.attackArea.x -= entity.getSpeed();
                        if (entity.attackArea.intersects(target[i].hitBox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.attackArea.x += entity.getSpeed();
                        if (entity.attackArea.intersects(target[i].hitBox)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                entity.attackArea.x = entity.attackAreaDefaultx;
                entity.attackArea.y = entity.attackAreaDefaulty;
                target[i].hitBox.x = target[i].hitBoxDefeaultX;
                target[i].hitBox.y = target[i].hitBoxDefeaultX;
            }
        }

        return index;
    }

    public void checkPlayer(Entity entity) {
        // get solid area position for both entity and object
        entity.hitBox.x += entity.getWorldX();
        entity.hitBox.y += entity.getWorldY();

        Window.player.hitBox.x = Window.player.getWorldX() + Window.player.hitBox.x;
        Window.player.hitBox.y = Window.player.getWorldY() + Window.player.hitBox.y;
        switch (entity.direction) {
            case "up":
                entity.hitBox.y -= entity.getSpeed();
                if (entity.hitBox.intersects(Window.player.hitBox)) {
                    entity.collisionOn = true;

                }
                break;
            case "down":
                entity.hitBox.y += entity.getSpeed();
                if (entity.hitBox.intersects(Window.player.hitBox)) {
                    entity.collisionOn = true;

                }
                break;
            case "left":
                entity.hitBox.x -= entity.getSpeed();
                if (entity.hitBox.intersects(Window.player.hitBox)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.hitBox.x += entity.getSpeed();
                if (entity.hitBox.intersects(Window.player.hitBox)) {
                    entity.collisionOn = true;

                }
                break;
        }
        entity.hitBox.x = entity.hitBoxDefeaultX;
        Window.player.hitBox.x = Window.player.hitBoxDefeaultX;
        Window.player.hitBox.y = Window.player.hitBoxDefeaultY;
    }
}
