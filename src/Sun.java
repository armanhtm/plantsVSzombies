import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
/**
 * @author Arman Hatami
 * @version 1.0
 * sun
 */
public class Sun extends Element implements Serializable {
    //end of yard
    int endHeight;
    //is falling type or not
    boolean fallingType;
    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Sun(int x, int y, int height, int width,boolean fallingType) {
        super(x, y, height, width, "assets/images/sun.gif");
        if(fallingType){
            this.y = 0;
            this.x = Math.abs(new Random().nextInt()) % 700 + 300;
        }
        this.fallingType = fallingType;
        
        endHeight = 550;
    }

    /**
     * action method of sun
     * @param elements
     */
    public void Action(ArrayList<Element> elements) {
        if(fallingType){
           dropSun();
        }
    }

    /**
     * drop sun method
     */
    public void dropSun(){
        if(y < endHeight){
            y += 1;
        }
    }
}
