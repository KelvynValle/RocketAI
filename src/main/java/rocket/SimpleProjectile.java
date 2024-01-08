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
public class SimpleProjectile extends Rocket {

    public static BufferedImage sprite;

    public SimpleProjectile(double initialAngle, double acceleration) {
        super(100, 99, 150, 100, initialAngle, "");
        this.indexSprite = 2;
    }

    @Override
    public BufferedImage getImage() {
        return SimpleProjectile.sprite;
    }

    public static void loadImage() throws IOException {
        SimpleProjectile.sprite = ImageIO.read(new File("c:\\ball_1.png"));
    }
}
