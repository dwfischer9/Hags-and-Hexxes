import java.util.ArrayList;

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
        int entityLeftWorldX = entity.getWorldX() + entity.getHitBox().x;
        int entityRightWorldX = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width;
        int entityTopWorldY = entity.getWorldY() + entity.getHitBox().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height;

        int lCol = entityLeftWorldX / Tile.TILESIZE; // get the left and right column number of the entity's hitbox, in
                                                     // tile coordinates
        int rCOl = entityRightWorldX / Tile.TILESIZE;
        int tRow = entityTopWorldY / Tile.TILESIZE; // get the top and bottom row number of the entity's hitbox, in tile
                                                    // coordinates
        int bRow = entityBottomWorldY / Tile.TILESIZE;
        TileManager tm = window.tileM;
        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                tRow = (entityTopWorldY - entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][tRow];
                tileNum2 = tm.mapTileNum[rCOl][tRow];
                if (tm.tiles[tileNum1].isCollision() == true
                        || tm.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;
            case "down":
                bRow = (entityBottomWorldY + entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][bRow];
                tileNum2 = tm.mapTileNum[rCOl][bRow];
                if (tm.tiles[tileNum1].isCollision() == true
                        || tm.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;
            case "left":
                lCol = (entityLeftWorldX - entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][tRow];
                tileNum2 = tm.mapTileNum[lCol][bRow];
                if (tm.tiles[tileNum1].isCollision() == true
                        || tm.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;
            case "right":
                rCOl = (entityRightWorldX + entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[rCOl][tRow];
                tileNum2 = tm.mapTileNum[rCOl][bRow];
                if (tm.tiles[tileNum1].isCollision() == true
                        || tm.tiles[tileNum2].isCollision() == true)
                    entity.collisionOn = true;
                break;

        }

    }

    public Item checkObject(Entity entity, boolean player) {
        Item contact = null;
        for (Item item : Game.items.values()) {
            entity.getHitBox().x += entity.getWorldX();
            entity.getHitBox().y += entity.getWorldY();
            item.HITBOX.x += item.worldX;
            item.HITBOX.y += item.worldY;

            switch (entity.direction) {
                case "up":
                    entity.getHitBox().y -= entity.getSpeed();
                    if (entity.getHitBox().intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
                case "down":
                    entity.getHitBox().y += entity.getSpeed();
                    if (entity.getHitBox().intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
                case "left":
                    entity.getHitBox().x -= entity.getSpeed();
                    if (entity.getHitBox().intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
                case "right":
                    entity.getHitBox().x += entity.getSpeed();
                    if (entity.getHitBox().intersects(item.HITBOX)) {
                        if (item.collision)
                            entity.collisionOn = true;
                        if (player) {
                            contact = item;
                        }
                    }
                    break;
            }

            entity.getHitBox().x = entity.hitBoxDefeaultX;
            entity.getHitBox().y = entity.hitBoxDefeaultY;
            item.HITBOX.x = item.HITBOXDEFAULTX;
            item.HITBOX.y = item.HITBOXDEFAULTY;

        }
        return contact;
    }

    public int checkEntity(Entity entity, ArrayList<Entity> target) {
        int index = 999;

        for (int i = 0; i < target.size(); i++) {
            if (target.get(i) != null && entity != target.get(i)) {
                // get solid area position for both entity and object
                entity.getHitBox().x += entity.getWorldX();
                entity.getHitBox().y += entity.getWorldY();

                target.get(i).getHitBox().x += target.get(i).getWorldX();
                target.get(i).getHitBox().y += target.get(i).getWorldY();
                switch (entity.direction) {
                    case "up":

                        entity.getHitBox().y -= entity.getSpeed();
                        if (entity.getHitBox().intersects(target.get(i).getHitBox())) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":

                        entity.getHitBox().y += entity.getSpeed();
                        if (entity.getHitBox().intersects(target.get(i).getHitBox())) {
                            index = i;
                            entity.collisionOn = true;

                        }
                        break;
                    case "left":
                        entity.getHitBox().x -= entity.getSpeed();
                        if (entity.getHitBox().intersects(target.get(i).getHitBox())) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.getHitBox().x += entity.getSpeed();
                        if (entity.getHitBox().intersects(target.get(i).getHitBox())) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                entity.getHitBox().x = entity.hitBoxDefeaultX;
                entity.getHitBox().y = entity.hitBoxDefeaultY;
                target.get(i).getHitBox().x = target.get(i).hitBoxDefeaultX;
                target.get(i).getHitBox().y = target.get(i).hitBoxDefeaultX;
            }
        }

        return index;
    }

    public int checkAttackEntity(Player player, ArrayList<Entity> target) {
        int index = 999;

        for (int i = 0; i < target.size(); i++) {
            if (target.get(i) != null) {
                // get solid area position for both entity and object
                player.attackArea.x += player.getWorldX();
                player.attackArea.y += player.getWorldY();

                target.get(i).getHitBox().x += target.get(i).getWorldX();
                target.get(i).getHitBox().y += target.get(i).getWorldY();
                switch (player.direction) {

                    case "up":

                        player.attackArea.y -= player.getSpeed();

                        if (player.attackArea.intersects(target.get(i).getHitBox())) {

                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        player.attackArea.y += player.getSpeed();
                        player.attackArea.y -= 2 * Tile.TILESIZE;
                        if (player.attackArea.intersects(target.get(i).getHitBox())) {

                            index = i;
                            player.collisionOn = true;

                        }
                        break;
                    case "left":
                        player.attackArea.x -= player.getSpeed();
                        if (player.attackArea.intersects(target.get(i).getHitBox())) {
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        player.attackArea.x += player.getSpeed();
                        player.attackArea.x -= 2 * Tile.TILESIZE;
                        if (player.attackArea.intersects(target.get(i).getHitBox())) {
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                player.attackArea.x = player.attackAreaDefaultx;
                player.attackArea.y = player.attackAreaDefaulty;
                target.get(i).getHitBox().x = target.get(i).hitBoxDefeaultX;
                target.get(i).getHitBox().y = target.get(i).hitBoxDefeaultY;
            }
        }

        return index;
    }

    public void checkPlayer(Entity entity) {
        // get solid area position for both entity and object
        entity.getHitBox().x += entity.getWorldX();
        entity.getHitBox().y += entity.getWorldY();

        Game.player.getHitBox().x = Game.player.getWorldX() + Game.player.getHitBox().x;
        Game.player.getHitBox().y = Game.player.getWorldY() + Game.player.getHitBox().y;
        switch (entity.direction) {
            case "up":
                entity.getHitBox().y -= entity.getSpeed();
                if (entity.getHitBox().intersects(Game.player.getHitBox())) {
                    entity.collisionOn = true;

                }
                break;
            case "down":
                entity.getHitBox().y += entity.getSpeed();
                if (entity.getHitBox().intersects(Game.player.getHitBox())) {
                    entity.collisionOn = true;

                }
                break;
            case "left":
                entity.getHitBox().x -= entity.getSpeed();
                if (entity.getHitBox().intersects(Game.player.getHitBox())) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.getHitBox().x += entity.getSpeed();
                if (entity.getHitBox().intersects(Game.player.getHitBox())) {
                    entity.collisionOn = true;

                }
                break;
        }
        entity.getHitBox().x = entity.hitBoxDefeaultX;
        Game.player.getHitBox().x = Game.player.hitBoxDefeaultX;
        Game.player.getHitBox().y = Game.player.hitBoxDefeaultY;
    }
}
