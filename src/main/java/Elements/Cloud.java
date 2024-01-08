/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Elements;

import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author kelvyn valle
 */
public class Cloud {

    public boolean killed = false;
    private boolean growing = false;
    public double speedX;
    public double speedY;
    public double globalScale;
    public double opacity;
    public double currentScale;
    public double x;
    public double y;
    public int cloudIndex;
    private static BufferedImage[] clouds = new BufferedImage[9];

    public Cloud(double speedX, double speedY, boolean sideBorn) {
        Random rand = new Random();
        this.currentScale = 1;
        this.globalScale = (500 + rand.nextInt(500)) / 1000.0;
        this.opacity = (200 + rand.nextInt(500)) / 1000.0;
        this.cloudIndex = rand.nextInt(8);
        this.speedX = speedX;
        this.speedY = speedY;
        this.x = sideBorn ? (rand.nextInt(1000) < 500 ? -200 : 1000) : rand.nextInt(800);
        this.y = rand.nextInt(600);
    }

    //load the sprites of the clouds
    public static void loadImage() throws IOException {
        for (int i = 1; i < 10; i++) {
            clouds[i - 1] = ImageIO.read(new File("src\\main\\images\\cloud_" + i + ".png"));
        }
    }

    //draws the sprite of the cloud considering opacity and global and local scale 
    public void draw(Graphics2D g) {
        BufferedImage cloud = Cloud.clouds[this.cloudIndex];
        double localScale = this.globalScale * this.currentScale;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) this.opacity));
        g.drawImage(cloud, (int) this.x, (int) this.y, (int) (cloud.getWidth() * localScale), (int) (cloud.getHeight() * localScale), null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    //refreshes the cloud location and scale
    public void refresh(boolean paused) {
        if (!paused) {
            if (this.growing) {
                this.currentScale += 0.001;
            } else {
                this.currentScale -= 0.001;
            }
            this.x += this.speedX;
            this.y += this.speedY;
            this.growing = this.currentScale <= 0.95 ? true : (this.currentScale >= 1 ? false : this.growing);
            this.killed = this.x < -200 || this.x > 1000 ? true : false;
        }
    }

    //renew the current cloud
    public static Cloud renew(Cloud cloud) {
        return cloud.killed ? new Cloud(cloud.speedX, cloud.speedY, true) : cloud;
    }
}
