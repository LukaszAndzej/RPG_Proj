package proj;

import proj.entity.Entity;
import proj.objects.SuperObject;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        switch (entity.direction) {
            case "up":
                checkVerticalCollision(entity, entityLeftCol, entityRightCol, (entityTopWorldY - entity.speed) / gp.tileSize);
                break;
            case "down":
                checkVerticalCollision(entity, entityLeftCol, entityRightCol, (entityBottomWorldY + entity.speed) / gp.tileSize);
                break;
            case "left":
                checkHorizontalCollision(entity, (entityLeftWorldX - entity.speed) / gp.tileSize, entityTopRow, entityBottomRow);
                break;
            case "right":
                checkHorizontalCollision(entity, (entityRightWorldX + entity.speed) / gp.tileSize, entityTopRow, entityBottomRow);
                break;
        }
    }

    private void checkVerticalCollision(Entity entity, int leftCol, int rightCol, int row) {
        int tileNum1 = gp.tileM.mapTileNum[leftCol][row];
        int tileNum2 = gp.tileM.mapTileNum[rightCol][row];
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    private void checkHorizontalCollision(Entity entity, int col, int topRow, int bottomRow) {
        int tileNum1 = gp.tileM.mapTileNum[col][topRow];
        int tileNum2 = gp.tileM.mapTileNum[col][bottomRow];
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++) {
            SuperObject obj = gp.obj[i];
            if (obj != null) {
                updateSolidAreaPosition(entity, obj);

                if (checkCollision(entity, obj) && player) {
                    index = i;
                }

                resetSolidAreaPosition(entity, obj);
            }
        }
        return index;
    }

    private void updateSolidAreaPosition(Entity entity, SuperObject obj) {
        entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
        entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;
        obj.solidArea.x = obj.worldX + obj.solidAreaDefaultX;
        obj.solidArea.y = obj.worldY + obj.solidAreaDefaultY;
    }

    private void resetSolidAreaPosition(Entity entity, SuperObject obj) {
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        obj.solidArea.x = obj.solidAreaDefaultX;
        obj.solidArea.y = obj.solidAreaDefaultY;
    }

    private boolean checkCollision(Entity entity, SuperObject obj) {
        boolean collisionDetected = false;
        int speed = entity.speed;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= speed;
                break;
            case "down":
                entity.solidArea.y += speed;
                break;
            case "left":
                entity.solidArea.x -= speed;
                break;
            case "right":
                entity.solidArea.x += speed;
                break;
        }

        if (entity.solidArea.intersects(obj.solidArea)) {
            if (obj.collision) {
                entity.collisionOn = true;
            }
            collisionDetected = true;
        }

        return collisionDetected;
    }
}
