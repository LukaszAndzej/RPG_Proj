package proj;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    private static final Map<Integer, Runnable> keyPressMap = new HashMap<>();
    private static final Map<Integer, Runnable> keyReleaseMap = new HashMap<>();

    public KeyHandler() {
        keyPressMap.put(KeyEvent.VK_W, () -> upPressed = true);
        keyPressMap.put(KeyEvent.VK_S, () -> downPressed = true);
        keyPressMap.put(KeyEvent.VK_A, () -> leftPressed = true);
        keyPressMap.put(KeyEvent.VK_D, () -> rightPressed = true);

        keyReleaseMap.put(KeyEvent.VK_W, () -> upPressed = false);
        keyReleaseMap.put(KeyEvent.VK_S, () -> downPressed = false);
        keyReleaseMap.put(KeyEvent.VK_A, () -> leftPressed = false);
        keyReleaseMap.put(KeyEvent.VK_D, () -> rightPressed = false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (keyPressMap.containsKey(code)) {
            keyPressMap.get(code).run();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (keyReleaseMap.containsKey(code)) {
            keyReleaseMap.get(code).run();
        }
    }

}
