/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceElements.Buttons;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author kelvyn valle
 */
public class StopButton extends MenuButton {

    public StopButton() throws IOException {
        super(320, 0);
        this.image = ImageIO.read(new File("src\\main\\images\\stop_button.png"));
        this.imageHover = ImageIO.read(new File("src\\main\\images\\stop_button_hover.png"));
    }

    //perform action on click of button
    @Override
    public void click(Game.RocketLauncher screen) {
        System.out.println("asd");
    }

}
