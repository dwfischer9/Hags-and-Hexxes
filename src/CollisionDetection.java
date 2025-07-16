import java.awt.Rectangle;
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
        TileManager tm = window.getTileManager();
        int tileNum1, tileNum2;

        switch (entity.getDirection()) {
            case "up" -> {
                tRow = (entityTopWorldY - entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][tRow];
                tileNum2 = tm.mapTileNum[rCOl][tRow];
            }
            case "down" -> {
                bRow = (entityBottomWorldY + entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][bRow];
                tileNum2 = tm.mapTileNum[rCOl][bRow];
            }
            case "left" -> {
                lCol = (entityLeftWorldX - entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][tRow];
                tileNum2 = tm.mapTileNum[lCol][bRow];
            }
            case "right" -> {
                rCOl = (entityRightWorldX + entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[rCOl][tRow];
                tileNum2 = tm.mapTileNum[rCOl][bRow];
            }
            case "up-right" -> {
                // Check both up and right directions
                int upRow = (entityTopWorldY - entity.getSpeed()) / Tile.TILESIZE;
                int rightCol = (entityRightWorldX + entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][upRow]; // Check up
                tileNum2 = tm.mapTileNum[rightCol][tRow]; // Check right
            }
            case "up-left" -> {
                // Check both up and left directions
                int upRow = (entityTopWorldY - entity.getSpeed()) / Tile.TILESIZE;
                int leftCol = (entityLeftWorldX - entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][upRow]; // Check up
                tileNum2 = tm.mapTileNum[leftCol][tRow]; // Check left
            }
            case "down-right" -> {
                // Check both down and right directions
                int downRow = (entityBottomWorldY + entity.getSpeed()) / Tile.TILESIZE;
                int rightCol = (entityRightWorldX + entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][downRow]; // Check down
                tileNum2 = tm.mapTileNum[rightCol][tRow]; // Check right
            }
            case "down-left" -> {
                // Check both down and left directions
                int downRow = (entityBottomWorldY + entity.getSpeed()) / Tile.TILESIZE;
                int leftCol = (entityLeftWorldX - entity.getSpeed()) / Tile.TILESIZE;
                tileNum1 = tm.mapTileNum[lCol][downRow]; // Check down
                tileNum2 = tm.mapTileNum[leftCol][tRow]; // Check left
            }
            default -> {
                tileNum1 = 0;
                tileNum2 = 0;
            }
        }
        if (tm.tiles[tileNum1].isCollision() == true
                || tm.tiles[tileNum2].isCollision() == true)
            entity.collisionOn = true;

    }

    public Item checkObject(Entity entity, boolean player) {
        Item contact = null;
        Game game = Game.getInstance();
        if (game == null || game.getItems() == null) return null;
        
        for (Item item : game.getItems().values()) {
            entity.getHitBox().x += entity.getWorldX();
            entity.getHitBox().y += entity.getWorldY();
            item.hitBox.x += item.worldX;
            item.hitBox.y += item.worldY;

            switch (entity.getDirection()) {
                case "up" -> entity.getHitBox().y -= entity.getSpeed();
                case "down" -> entity.getHitBox().y += entity.getSpeed();
                case "left" -> entity.getHitBox().x -= entity.getSpeed();
                case "right" -> entity.getHitBox().x += entity.getSpeed();
                case "up-right" -> {
                    entity.getHitBox().y -= entity.getSpeed();
                    entity.getHitBox().x += entity.getSpeed();
                }
                case "up-left" -> {
                    entity.getHitBox().y -= entity.getSpeed();
                    entity.getHitBox().x -= entity.getSpeed();
                }
                case "down-right" -> {
                    entity.getHitBox().y += entity.getSpeed();
                    entity.getHitBox().x += entity.getSpeed();
                }
                case "down-left" -> {
                    entity.getHitBox().y += entity.getSpeed();
                    entity.getHitBox().x -= entity.getSpeed();
                }
            }
            if (entity.getHitBox().intersects(item.hitBox)) {
                if (item.collision)
                    entity.collisionOn = true;
                if (player) {
                    contact = item;
                }
            }
            entity.resetHitBox();
            item.resetHitBox();
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
                switch (entity.getDirection()) {
                    case "up" -> entity.getHitBox().y -= entity.getSpeed();
                    case "down" -> entity.getHitBox().y += entity.getSpeed();
                    case "left" -> entity.getHitBox().x -= entity.getSpeed();
                    case "right" -> entity.getHitBox().x += entity.getSpeed();
                    case "up-right" -> {
                        entity.getHitBox().y -= entity.getSpeed();
                        entity.getHitBox().x += entity.getSpeed();
                    }
                    case "up-left" -> {
                        entity.getHitBox().y -= entity.getSpeed();
                        entity.getHitBox().x -= entity.getSpeed();
                    }
                    case "down-right" -> {
                        entity.getHitBox().y += entity.getSpeed();
                        entity.getHitBox().x += entity.getSpeed();
                    }
                    case "down-left" -> {
                        entity.getHitBox().y += entity.getSpeed();
                        entity.getHitBox().x -= entity.getSpeed();
                    }
                }
                if (entity.getHitBox().intersects(target.get(i).getHitBox())) {
                    entity.collisionOn = true;
                    index = i;
                }
                entity.resetHitBox();

                target.get(i).resetHitBox();
            }
        }

        return index;
    }

    public int checkAttackEntity(Player player, ArrayList<Entity> target) {
        int index = 999;

        for (int i = 0; i < target.size(); i++) {
            if (target.get(i) != null) {
                // Create a temporary attack area positioned relative to player's current position
                Rectangle tempAttackArea = new Rectangle(
                    player.getWorldX() + player.attackArea.x,
                    player.getWorldY() + player.attackArea.y,
                    player.attackArea.width,
                    player.attackArea.height
                );

                target.get(i).getHitBox().x += target.get(i).getWorldX();
                target.get(i).getHitBox().y += target.get(i).getWorldY();
                
                if (tempAttackArea.intersects(target.get(i).getHitBox())) {
                    player.collisionOn = true;
                    index = i;
                }
                target.get(i).resetHitBox();
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity) {
        Game game = Game.getInstance();
        if (game == null) return;
        
        Player player = game.getPlayer();
        if (player == null) return;
        
        // get solid area position for both entity and object
        entity.getHitBox().x += entity.getWorldX();
        entity.getHitBox().y += entity.getWorldY();

        player.getHitBox().x = player.getWorldX() + player.getHitBox().x;
        player.getHitBox().y = player.getWorldY() + player.getHitBox().y;
        switch (entity.getDirection()) {
            case "up" -> entity.getHitBox().y -= entity.getSpeed();
            case "down" -> entity.getHitBox().y += entity.getSpeed();
            case "left" -> entity.getHitBox().x -= entity.getSpeed();
            case "right" -> entity.getHitBox().x += entity.getSpeed();
            case "up-right" -> {
                entity.getHitBox().y -= entity.getSpeed();
                entity.getHitBox().x += entity.getSpeed();
            }
            case "up-left" -> {
                entity.getHitBox().y -= entity.getSpeed();
                entity.getHitBox().x -= entity.getSpeed();
            }
            case "down-right" -> {
                entity.getHitBox().y += entity.getSpeed();
                entity.getHitBox().x += entity.getSpeed();
            }
            case "down-left" -> {
                entity.getHitBox().y += entity.getSpeed();
                entity.getHitBox().x -= entity.getSpeed();
            }
        }
        if (entity.getHitBox().intersects(player.getHitBox()))
            entity.collisionOn = true;
        
        entity.resetHitBox();
        player.resetHitBox();
    }
}
