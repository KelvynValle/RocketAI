/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceElements.Buttons;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import Game.Screen;

/**
 *
 * @author kelvyn valle
 */
public class StartButton extends MenuButton {

    private boolean isStart = true;

    public StartButton() throws IOException {
        super(220, 0);
        this.changeStatusStartStop();
    }

    //change button to start when paused and pause when started
    public void changeStatusStartStop() throws IOException {
        this.isStart = !this.isStart;
        this.image = this.isStart ? ImageIO.read(new File("src\\main\\images\\start_button.png")) : ImageIO.read(new File("src\\main\\images\\pause_button.png"));
        this.imageHover = this.isStart ? ImageIO.read(new File("src\\main\\images\\start_button_hover.png")) : ImageIO.read(new File("src\\main\\images\\pause_button_hover.png"));
    }

    //perform action on click of button
    @Override
    public void click(Game.RocketLauncher screen) {
        try {
            this.changeStatusStartStop();
            if (this.isStart) {
                screen.pauseGame();
            } else {
                screen.unpauseGame();
            }
        } catch (IOException ex) {
            Logger.getLogger(StartButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
