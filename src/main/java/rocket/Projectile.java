/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rocket;

import java.awt.image.BufferedImage;

/**
 *
 * @author kelvyn valle
 */
public interface Projectile {

    public void refresh(double windX, double windY);

    public boolean hitTheGoal(double initialXLocation, double initialYLocation);

    public boolean outOfArea(double initialXLocation);

    public BufferedImage getImage();
}
