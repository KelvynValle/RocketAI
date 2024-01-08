/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 *
 * @author kelvyn valle
 */
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Screen extends JPanel implements MouseMotionListener, MouseListener {

    public static Point mouseLocation;
    public RocketLauncher game;

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseLocation = new Point(e.getX(), e.getY());
    }

    public Screen() throws IOException {
        this.game = new RocketLauncher();
        this.mouseLocation = new Point(0, 0);
        addMouseMotionListener(this);
        addMouseListener(this);
        setPreferredSize(new Dimension(800, 600));
        Timer timer = new Timer(10, e -> repaint());
        timer.start();
    }

    // The paintComponent method that draws the bitmap on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.game.drawBackground(g, this);
        this.game.drawClouds(g, this);
        if (!this.game.isPaused()) {
            this.game.refresh(g, this);
        } else {
            this.game.pausedRefresh(g, this);
        }

        this.game.drawInformation(g);
        this.game.menu.refresh(mouseLocation.x, mouseLocation.y, g, this);

    }

    // The main method that creates a frame and adds the panel to it
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Rocket AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen gameScreen = new Screen();
        frame.add(gameScreen);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.game.click();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
