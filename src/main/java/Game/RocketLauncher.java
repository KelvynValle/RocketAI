/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import InterfaceElements.Buttons.MenuButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author kelvyn valle
 */
public class RocketLauncher {

    private boolean paused = false;
    private boolean started = false;
    private int hits = 0;
    private int numberOfRNA = 10000;
    private double windX = 0;
    private double windY = 0;
    private double initialXLocation;
    private double initialYLocation;
    private BufferedImage background;
    public InterfaceElements.Menu menu;
    private MenuButton currentButton = null;
    private Elements.Cloud[] clouds;
    private rocket.Rocket[] rockets;
    private RNA.NeuralNetwork[] neuralNetworks;

    //checks if the game is paused
    public boolean isPaused() {
        return this.paused;
    }

    //checks if the game has a rocket that is not stopped
    private boolean allRocketStoped() {
        boolean stoped = true;
        for (int i = 0; i < numberOfRNA; i++) {
            stoped = rockets[i].stoped && stoped;
        }
        return stoped;
    }

    //refresh the rockets in the game
    public void refresh(Graphics g, JPanel screen) {
        for (int i = 0; i < this.neuralNetworkCount(); i++) {
            if (!this.rockets[i].stoped) {
                rockets[i].refresh(this.windX, this.windY);
                if (i < 100) {
                    rockets[i].draw(g, screen);
                }
                if (rockets[i].outOfArea(this.initialXLocation)) {
                    rockets[i].stoped = true;
                    neuralNetworks[i].error = this.rockets[i].getDistance(this.initialXLocation, this.initialYLocation);
                    if (rockets[i].hitTheGoal(this.initialXLocation, this.initialYLocation)) {
                        neuralNetworks[i].error = -10000 + this.rockets[i].getDistance(this.initialXLocation, this.initialYLocation);
                        this.hits++;
                    }
                }
            } else {
                if (i < 100) {
                    rockets[i].nextExplosionSprite(g, screen);
                }
            }
        }
        this.currentButton = this.menu.getHoverButton();
        if (this.allRocketStoped()) {
            this.restart();
        }
    }
    
    //draws the game informations on the screen
    public void drawInformation(Graphics g) {
        g.drawString("Hits: " + this.hits, 700, 550);
    }

    //draw and refresh the game when it is paused
    public void pausedRefresh(Graphics g, JPanel screen) {
        for (int i = 0; i < this.neuralNetworkCount(); i++) {
            if (!this.rockets[i].stoped) {
                if (i < 100) {
                    rockets[i].draw(g, screen);
                }
                if (rockets[i].outOfArea(this.initialXLocation)) {
                    rockets[i].stoped = true;
                    neuralNetworks[i].error = this.rockets[i].getDistance(this.initialXLocation, this.initialYLocation);
                    if (rockets[i].hitTheGoal(this.initialXLocation, this.initialYLocation)) {
                        neuralNetworks[i].error = -10000 + this.rockets[i].getDistance(this.initialXLocation, this.initialYLocation);
                        this.hits++;
                    }
                }
            } else {
                if (i < 100) {
                    rockets[i].currentExplosionSprite(g, screen);
                }
            }
        }
    }

    //get the number of neural networks
    public int neuralNetworkCount() {
        return this.numberOfRNA;
    }

    //pause the game
    public void pauseGame() {
        this.paused = true;
    }

    //unpause the game
    public void unpauseGame() {
        this.paused = false;
    }

    //restart the game, reloading the neural networks and rockets
    public final void restart() {
        this.hits = 0;
        Random rand = new Random();
        this.windX = rand.nextInt(1000) < 800 ? (rand.nextInt(100) / 100.0) - 0.5 : (rand.nextInt(1000) / 100.0) - 5;
        this.windY = rand.nextInt(1000) < 800 ? (rand.nextInt(100) / 100.0) - 0.5 : (rand.nextInt(1000) / 100.0) - 5;
        this.initialXLocation = 400 + rand.nextInt(4000) / 10;
        this.initialYLocation = rand.nextInt(6000) / 10;
        this.startClouds();
        if (!started) {
            this.rockets = new rocket.Rocket[numberOfRNA];
            this.neuralNetworks = new RNA.NeuralNetwork[numberOfRNA];
            started = true;
            for (int i = 0; i < numberOfRNA; i++) {
                /*
        inputs:
            distance
            wind speedX
            wind speedY
        outputs:
            angle
            acceleration
                 */
                this.neuralNetworks[i] = new RNA.NeuralNetwork(new int[]{4, 6, 8, 6});
                double[] value = this.neuralNetworks[i].getValue(new double[]{this.initialXLocation / 800, this.initialYLocation / 600, (5 + windX) / 5, (windY + 5) / 5});
                double[] hotStick = new double[]{value[2], value[3], value[4]};
                double max = Math.max(Math.max(hotStick[0], hotStick[1]), hotStick[2]);
                int index = max == hotStick[0] ? 0 : (max == hotStick[1] ? 1 : 2);
                rockets[i] = index == 0 ? new rocket.LongRangeRocket((value[0] * -1) * 180 / Math.PI, value[1] * 20) : (index == 1 ? new rocket.ShortRangeRocket((value[0] * -1) * 180 / Math.PI, value[1] * 20) : new rocket.SimpleProjectile((value[0] * -1) * 180 / Math.PI, value[1] * 20));
                this.neuralNetworks[i].major = value[0] != 0 ? -50 : 0;
            }
        } else {
            Arrays.sort(this.neuralNetworks, new Comparator<RNA.NeuralNetwork>() {
                public int compare(RNA.NeuralNetwork r1, RNA.NeuralNetwork r2) {
                    return r1.error < r2.error ? 1 : (r1.error == r2.error ? 0 : -1);//if r1 is greater return +1, if b2 is smaller return -1 otherwise 0
                }
            });
            for (int i = 1; i < numberOfRNA; i++) {
                this.neuralNetworks[i] = RNA.NeuralNetwork.mix(this.neuralNetworks[0], this.neuralNetworks[i - 1]);
            }
            for (int i = 0; i < numberOfRNA; i++) {
                double[] value = this.neuralNetworks[i].getValue(new double[]{this.initialXLocation / 800, this.initialYLocation / 600, (5 + windX) / 10, (5 + windY) / 10});
                this.neuralNetworks[i].major = value[0] != 0 ? -50 : 0;
                System.out.println("Acceleration:" + value[0] * 1 + ", Angle:" + value[1] + ", LR:" + value[2] + ", SR:" + value[3] + ", Ball:" + value[4]);//(
                double[] hotStick = new double[]{value[2], value[3], value[4]};
                double max = Math.max(Math.max(hotStick[0], hotStick[1]), hotStick[2]);
                int index = max == hotStick[0] ? 0 : (max == hotStick[1] ? 1 : 2);
                rockets[i] = index == 0 ? new rocket.LongRangeRocket((value[0] * -1) * 180 / Math.PI, value[1] * 20) : (index == 1 ? new rocket.ShortRangeRocket((value[0] * -1) * 180 / Math.PI, value[1] * 20) : new rocket.SimpleProjectile((value[0] * -1) * 180 / Math.PI, value[1] * 20));
            }
        }

    }

    //load the background image
    private void loadBackground() throws IOException {
        this.background = ImageIO.read(new File("src\\main\\images\\background.png"));
    }

    public RocketLauncher() {
        try {
            this.clouds = new Elements.Cloud[20];
            this.loadBackground();
            Elements.Cloud.loadImage();
            rocket.Rocket.loadExplosionSprites();
            rocket.LongRangeRocket.loadImage();
            rocket.ShortRangeRocket.loadImage();
            rocket.SimpleProjectile.loadImage();
            this.menu = new InterfaceElements.Menu();
            this.restart();

        } catch (IOException ex) {
            Logger.getLogger(RocketLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //draws the background image
    public void drawBackground(Graphics g, JPanel panel) {
        g.drawImage(this.background, 0, 0, panel);
        g.fillRect((int) this.initialXLocation, (int) this.initialYLocation, 10, 100);
    }

    //start the game clouds
    private void startClouds() {
        for (int i = 0; i < this.clouds.length; i++) {
            this.clouds[i] = new Elements.Cloud(this.windX, this.windY, false);
        }
    }

    //draw the game clouds
    public void drawClouds(Graphics g, JPanel panel) {
        for (int i = 0; i < this.clouds.length; i++) {
            this.clouds[i].draw((Graphics2D) g);
            this.clouds[i].refresh(this.paused);
            this.clouds[i] = Elements.Cloud.renew(this.clouds[i]);
        }
    }

    //perform click on the game screen
    public void click() {
        if (this.currentButton != null) {
            this.currentButton.click(this);
        }
    }
}
