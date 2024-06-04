package proj.objects;

import javax.imageio.ImageIO;

public class Key extends SuperObject {
    
    public Key() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/proj/pic/key.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
