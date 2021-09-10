import javax.swing.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * lawn mower
 */
public class Lawnmower extends Element implements Serializable{
    ArrayList<Zombie> zombies;
    boolean move;
    /**
     * constructor method 
     * 
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Lawnmower(int x, int y, int height, int width) {
        super(x, y, height, width, "assets/images/lawn_mower.gif");
        move = false;
    }

    @Override
    /**
     * action method of lawn mower
     */
    public void Action(ArrayList<Element> elements) {
        if(!move) {
            zombies = new ArrayList<>();
            for (Element element : elements)
                if (element instanceof Zombie)
                    zombies.add((Zombie) element);
            isTouched(zombies);
        }
        else {
            this.x += 10;
            for(Zombie zombie : zombies) {
                if (new Rectangle(x, y, 30, 30).intersects(zombie.getX(), zombie.getY(), 80, 100)) {
                    elements.remove(zombie);
                }
            }
        }
        if(this.x > 1000)
            elements.remove(this);
    }

    /**
     * check if lawn mower is touched
     * @param zombies
     */
    public void isTouched(ArrayList<Zombie> zombies){
        for (Zombie zombie : zombies)
            if(zombie.getX() == 250 && zombie.getY() + 30 == this.getY()) {
                move = true;
                this.address = "assets/images/LawnmowerActivated.gif";
                return;
            }
    }
}

