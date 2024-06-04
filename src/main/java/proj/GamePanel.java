package proj;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JPanel;

import proj.entity.Player;
import proj.objects.SuperObject;
import proj.tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Stałe i ustawienia
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 3;
    public final int tileSize = ORIGINAL_TILE_SIZE * SCALE;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Ustawienia dla świata
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    private static final int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public UI ui = new UI(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Ustawianie preferowanego rozmiaru panelu
        this.setBackground(Color.black); // Ustawianie koloru tła panelu na czarny
        this.setDoubleBuffered(true); // Włączanie podwójnego buforowania dla panelu
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D)graphics;

        drawGame(g2);

        g2.dispose();
    }

    private void drawGame(Graphics2D g2) {
        tileM.draw(g2);

        for (SuperObject obj : this.obj) {
            if (obj != null) {
                obj.draw(g2, this);
            }
        }

        player.draw(g2);
        ui.draw(g2);
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            if (timer >= 1_000_000_000) {
                timer = 0;
            }
        }

    }
    
}
