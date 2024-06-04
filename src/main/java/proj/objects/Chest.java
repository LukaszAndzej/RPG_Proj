package proj.objects;

import javax.imageio.ImageIO;

public class Chest extends SuperObject {
    public Chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/proj/pic/chest.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
