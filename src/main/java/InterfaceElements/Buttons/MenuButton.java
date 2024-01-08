/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceElements.Buttons;

import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author kelvyn valle
 */
public class MenuButton {

    public BufferedImage image;
    public BufferedImage imageHover;
    public boolean hover = false;
    public int x;
    public int y;

    public MenuButton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //check if the cursor is hover the button
    public boolean hover(int x, int y) {
        this.hover = (x >= this.x && y >= this.y) && (x <= this.x + this.image.getWidth() && y <= this.y + this.image.getHeight());
        return this.hover;
    }

    //redraw the button on the image
    public void refresh(Graphics g, JPanel panel, int globalX, int globalY) {
        g.drawImage(this.hover ? imageHover : image, this.x + globalX, this.y + globalY, panel);
    }

    //perform click action
    public void click(Game.RocketLauncher screen) {
        System.out.println("Clicked");
    }
}
