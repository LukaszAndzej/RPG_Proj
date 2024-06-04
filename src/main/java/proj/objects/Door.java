package proj.objects;

import javax.imageio.ImageIO;

public class Door extends SuperObject {
    public Door() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/proj/pic/door.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
    
}
