package proj.objects;

import javax.imageio.ImageIO;

public class Flash extends SuperObject {
    public Flash() {
        name = "Flash";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/proj/pic/flash.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
