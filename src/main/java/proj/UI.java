package proj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import proj.objects.Key;

public class UI {
    private static final int MESSAGE_DURATION = 120;
    private static final int MESSAGE_FONT_SIZE = 30;
    private static final int KEY_TEXT_X = 90;
    private static final int KEY_TEXT_Y = 60;

    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String message = "";

    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    
    int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        Key key = new Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if (gameFinished) {
            drawGameFinishedMessage(g2);
        } else {
            drawGameUI(g2);
        }
    }


    private void drawGameFinishedMessage(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        String text = "Znalazles skarb!";
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - textLength / 2;
        int y = gp.screenHeight / 2 - (gp.tileSize * 3);
        g2.drawString(text, x, y);

        g2.setFont(arial_80B);
        g2.setColor(Color.yellow);
        text = "Brawo Wariacie!";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 2 - textLength / 2;
        y = gp.screenHeight / 2 + (gp.tileSize * 2);
        g2.drawString(text, x, y);

        gp.gameThread = null;
    }

    private void drawGameUI(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("Key = " + gp.player.hasKey, KEY_TEXT_X, KEY_TEXT_Y);

        if (messageOn) {
            drawMessage(g2);
        }
    }

    private void drawMessage(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont((float) MESSAGE_FONT_SIZE));
        g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

        messageCounter++;
        if (messageCounter > MESSAGE_DURATION) {
            messageCounter = 0;
            messageOn = false;
        }
    }
    
}
