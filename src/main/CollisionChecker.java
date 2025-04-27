package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // Check for tile collisions
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Check for collisions based on direction
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                checkTileCollision(entityLeftCol, entityTopRow, entityRightCol, entityTopRow, entity);
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                checkTileCollision(entityLeftCol, entityBottomRow, entityRightCol, entityBottomRow, entity);
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                checkTileCollision(entityLeftCol, entityTopRow, entityLeftCol, entityBottomRow, entity);
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                checkTileCollision(entityRightCol, entityTopRow, entityRightCol, entityBottomRow, entity);
                break;
        }
    }

    // Helper method to check tile collision
    private void checkTileCollision(int col1, int row1, int col2, int row2, Entity entity) {
        int tileNum1 = gp.tileM.mapTileNum[col1][row1];
        int tileNum2 = gp.tileM.mapTileNum[col2][row2];
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    // Check for object collisions
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Update solid area positions
                updateSolidArea(entity);
                updateSolidArea(gp.obj[i]);

                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                // Reset solid area positions
                resetSolidArea(entity);
                resetSolidArea(gp.obj[i]);
            }
        }
        return index;
    }

    // Check for entity collisions (e.g., with NPCs)
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Update solid area positions
                updateSolidArea(entity);
                updateSolidArea(target[i]);

                if (entity.solidArea.intersects(target[i].solidArea)) {
                    entity.collisionOn = true;
                    index = i;
                }

                // Reset solid area positions
                resetSolidArea(entity);
                resetSolidArea(target[i]);
            }
        }
        return index;
    }

    // Update solid area position
    private void updateSolidArea(Entity entity) {
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
    }

    // Reset solid area position to default
    private void resetSolidArea(Entity entity) {
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
    }
}