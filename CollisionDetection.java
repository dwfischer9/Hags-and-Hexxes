
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

        int entityLeftCol = entityLeftWorldX / Window.TILESIZE;
        int entityRightCol = entityRightWorldX / Window.TILESIZE;
        int entityTopRow = entityTopWorldY / Window.TILESIZE;
        int entityBottomRow = entityBottomWorldY / Window.TILESIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / Window.TILESIZE;
                tileNum1 = window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = window.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (window.tileM.tiles[tileNum1].isCollision() == true
                        || window.tileM.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / Window.TILESIZE;
                tileNum1 = window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = window.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (window.tileM.tiles[tileNum1].isCollision() == true
                        || window.tileM.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / Window.TILESIZE;
                tileNum1 = window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (window.tileM.tiles[tileNum1].isCollision() == true
                        || window.tileM.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / Window.TILESIZE;
                tileNum1 = window.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = window.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (window.tileM.tiles[tileNum1].isCollision() == true
                        || window.tileM.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;

        }

    }

    public Item checkObject(Entity entity, boolean player) {
        Item contact = null;
        for (Item item : window.items.values()) {
            entity.hitBox.x += entity.getWorldX();
            entity.hitBox.y += entity.getWorldY();
            item.HITBOX.x += item.worldX;
            item.HITBOX.y += item.worldY;

            switch (entity.direction) {
                case "up":
                    entity.hitBox.y -= entity.getSpeed();
                    if (entity.hitBox.intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
                case "down":
                    entity.hitBox.y += entity.getSpeed();
                    if (entity.hitBox.intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
                case "left":
                    entity.hitBox.x -= entity.getSpeed();
                    if (entity.hitBox.intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
                case "right":
                    entity.hitBox.x += entity.getSpeed();
                    if (entity.hitBox.intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
            }

            entity.hitBox.x = entity.hitBoxDefeaultX;
            entity.hitBox.y = entity.hitBoxDefeaultY;
            item.HITBOX.x = item.HITBOXDEFAULTX;
            item.HITBOX.y = item.HITBOXDEFAULTY;

        }
        return contact;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null && entity != target[i]) {
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

    public int checkAttackEntity(Player player, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // get solid area position for both entity and object
                player.attackArea.x += player.getWorldX();
                player.attackArea.y += player.getWorldY();

                target[i].hitBox.x += target[i].getWorldX();
                target[i].hitBox.y += target[i].getWorldY();
                switch (player.direction) {

                    case "up":

                        player.attackArea.y -= player.getSpeed();

                        if (player.attackArea.intersects(target[i].hitBox)) {

                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        player.attackArea.y += player.getSpeed();
                        player.attackArea.y -= 2 * Window.TILESIZE;
                        if (player.attackArea.intersects(target[i].hitBox)) {

                            index = i;
                            player.collisionOn = true;

                        }
                        break;
                    case "left":
                        player.attackArea.x -= player.getSpeed();
                        if (player.attackArea.intersects(target[i].hitBox)) {
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        player.attackArea.x += player.getSpeed();
                        player.attackArea.x -= 2 * Window.TILESIZE;
                        if (player.attackArea.intersects(target[i].hitBox)) {
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                player.attackArea.x = player.attackAreaDefaultx;
                player.attackArea.y = player.attackAreaDefaulty;
                target[i].hitBox.x = target[i].hitBoxDefeaultX;
                target[i].hitBox.y = target[i].hitBoxDefeaultY;
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
