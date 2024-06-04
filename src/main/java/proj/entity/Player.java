package proj.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import proj.GamePanel;
import proj.KeyHandler;

public class Player extends Entity {

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/proj/pic/boy_right_2.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            
            if (keyH.upPressed == true) {
                direction = "up";
            }
            
            if (keyH.downPressed == true) {
                direction = "down";
            }
            
            if (keyH.leftPressed == true) {
                direction = "left";
            }
            
            if (keyH.rightPressed == true) {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // if collision is false, player can move
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }


            spriteCounter++;

            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Zdobyles klucz!");
                    break;
            
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Drzwi otwarte!");
                    } else {
                        gp.ui.showMessage("Potrzebujesz klucza!");
                    }
                    break;
                case "Flash":
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("+5 do szybkosci!!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    break;
                default:
                    break;
            }

        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            default:
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
