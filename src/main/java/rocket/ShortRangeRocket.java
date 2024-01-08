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
public class ShortRangeRocket extends Rocket {

    private int counter = 0;
    public static BufferedImage sprite;

    public ShortRangeRocket(double initialAngle, double acceleration) {
        super(50, acceleration * 3, acceleration * 10, 100, initialAngle, "");
        this.indexSprite = 1;
    }

    //refreshes the rocket status
    @Override
    public void refresh(double windX, double windY) {
        super.refresh(windX, windY);
        if (this.counter == 50) {
            this.fuel = 40;
        }
        this.counter++;
    }

    //loads the rocket image
    public static void loadImage() throws IOException {
        ShortRangeRocket.sprite = ImageIO.read(new File("c:\\rocket_2.png"));
    }

    //returns the rocket sprite
    @Override
    public BufferedImage getImage() {
        return ShortRangeRocket.sprite;
    }
}
