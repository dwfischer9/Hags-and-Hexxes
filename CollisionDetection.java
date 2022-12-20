

public class CollisionDetection {
    Window window;
    public CollisionDetection(Window window){
        this.window = window;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.hitBox.x;
        int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWorldY = entity.worldY + entity.hitBox.y;
        int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
    
        int entityLeftCol = entityLeftWorldX/Window.tileSize;
        int entityRightCol = entityRightWorldX/Window.tileSize;
        int entityTopRow = entityTopWorldY/Window.tileSize;
        int entityBottomRow = entityBottomWorldY/Window.tileSize;

        int tileNum1,tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true; 
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true; 
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true; 
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/Window.tileSize;
                tileNum1 = Window.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = Window.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(Window.tileM.tiles[tileNum1].collision == true || Window.tileM.tiles[tileNum2].collision == true)
                    entity.collisionOn = true; 
                break;
        
        
        }


    }
}
