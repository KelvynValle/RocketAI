/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rocket;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author kelvyn valle
 */
public class Rocket implements Projectile {

    public boolean stoped = false;
    public int x;
    public int y;
    public int indexSprite;
    private int currentExplosionSprite = 0;
    public double fuel;
    public double fuelConsumption;
    public double acceleration;
    public double aeroDrag;
    public double angle;
    public double speedX;
    public double speedY;
    private String image;
    public static BufferedImage sprite;
    public static BufferedImage[] explosionSprites = new BufferedImage[8];

    //loads the explosion sprites
    public static void loadExplosionSprites() throws IOException {
        for (int i = 1; i < 8; i++) {
            explosionSprites[i - 1] = ImageIO.read(new File("src\\main\\images\\explosion_" + i + ".png"));
        }
    }

    //overridable method for load sprite images
    public static void loadImage() throws IOException {

    }

    //returns the rocket sprite
    public BufferedImage getImage() {
        return Rocket.sprite;
    }

    public Rocket(double fuel, double fuelConsumption, double acceleration, double aeroDrag, double initialAngle, String image) {
        this.fuel = fuel;
        this.fuelConsumption = fuelConsumption;
        this.acceleration = acceleration;
        this.aeroDrag = aeroDrag;
        this.angle = initialAngle;
        this.image = image;
        this.speedX = 0;
        this.speedY = 0;

    }

    public void nextExplosionSprite(Graphics g, JPanel screen) {
        if (this.currentExplosionSprite < explosionSprites.length) {
            g.drawImage(explosionSprites[this.currentExplosionSprite], (int) (100 + this.x / 50), (int) (300 + this.y / 50), screen);
            this.currentExplosionSprite++;
        }
    }

    public void currentExplosionSprite(Graphics g, JPanel screen) {
        if (this.currentExplosionSprite < explosionSprites.length) {
            g.drawImage(explosionSprites[this.currentExplosionSprite], (int) (100 + this.x / 50), (int) (300 + this.y / 50), screen);
        }
    }

    public double getDistance(double x, double y) {
        Point location = this.getLocation();
        return Math.sqrt(Math.pow(location.x - x, 2) + Math.pow(location.y - y, 2));
    }

    public void refresh(double windX, double windY) {
        if (!this.stoped) {
            if (this.fuel - fuelConsumption > 0) {
                this.fuel -= fuelConsumption;
                this.speedX += (this.acceleration * Math.cos(this.angle * Math.PI / 180));
                this.speedY += (this.acceleration * Math.sin(this.angle * Math.PI / 180) + 0.5);
                this.angle = Math.atan(this.speedY / this.speedX) * 180 / Math.PI;
            } else {
                this.speedY += 0.5;
                this.angle = Math.atan(this.speedY / this.speedX) * 180 / Math.PI;// / Math.PI;
            }
            x += this.speedX + windX;
            y += this.speedY + windY;
        }
    }

    private Point getLocation() {
        double x, y;
        x = 100 + this.x / 50;
        y = 300 + this.y / 50;
        return new Point((int) x, (int) y);
    }

    public void draw(Graphics g, JPanel panel) {
        Point location = this.getLocation();
        BufferedImage sprite = this.getImage();
        double rotationRequired = Math.toRadians(90 + this.angle);
        double locationX = sprite.getWidth() / 2;
        double locationY = sprite.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(op.filter(sprite, null), location.x, location.y, panel);
    }

    @Override
    public boolean outOfArea(double initialXLocation) {
        Point location = getLocation();
        if (location.x > initialXLocation || location.y < -1200 || location.y > 600) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hitTheGoal(double initialXLocation, double initialYLocation) {
        Point location = getLocation();
        if (((location.x > initialXLocation - 20 && location.x < initialXLocation + 20) && (location.y > initialYLocation && location.y < initialYLocation + 100))) {
            return true;
        }
        return false;
    }
}
