/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceElements;

import InterfaceElements.Buttons.*;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author kelvyn valle
 */
public class Menu {

    public MenuButton[] internalButtons;
    public int x = 0;
    public int y = 500;

    public Menu() throws IOException {
        internalButtons = new MenuButton[3];
        internalButtons[0] = new StartButton();
        internalButtons[1] = new StopButton();
        internalButtons[2] = new OptionsButton();
    }

    //get hover button, if has
    public MenuButton getHoverButton() {
        for (MenuButton menuButton : internalButtons) {
            if (menuButton.hover) {
                return menuButton;
            }
        }
        return null;
    }

    //redraw elements
    public void refresh(int cursorX, int cursorY, Graphics g, JPanel panel) {
        for (MenuButton menuButton : internalButtons) {
            menuButton.hover(cursorX - this.x, cursorY - this.y);
            menuButton.refresh(g, panel, this.x, this.y);
        }
    }
}
