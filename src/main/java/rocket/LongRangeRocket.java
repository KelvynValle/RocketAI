/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocket;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author kelvyn valle
 */
public class LongRangeRocket extends Rocket {

    public static BufferedImage sprite;

    //load the static sprite of rocket
    public static void loadImage() throws IOException {
        LongRangeRocket.sprite = ImageIO.read(new File("src\\main\\images\\rocket.png"));
    }

    public LongRangeRocket(double initialAngle, double acceleration) {
        super(100, Math.pow(acceleration / 10, 2) * 10, acceleration, 100, initialAngle, "");
        this.indexSprite = 0;
    }

    @Override
    public BufferedImage getImage() {
        return LongRangeRocket.sprite;
    }

}
