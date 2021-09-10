import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 * @author Arman Hatami
 * @version 1.0
 * pea shooter bullet
 */
public class Bullet extends Element{
    //die or alive
    boolean die;

    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Bullet(int x, int y, int height, int width) throws IOException {
        super(x, y, height, width, "assets/images/pea.png");
        die = false;
    }

    /**
     * attack method
     * @param zombie
     * @return result
     */
    private boolean attack(Zombie zombie) {

        if(new Rectangle(x, y, 30, 30).intersects(zombie.getX(), zombie.getY(), 80, 100)) {
            return true;
        }
        return false;
    }

    /**
     * fly method
     * @param zbList zombie list
     */
    private void flyAction(ArrayList<Zombie> zbList) {
        x+=5;
        for(int x=0;x<zbList.size();x++) {
            if(attack(zbList.get(x))) {
                zbList.get(x).hitByBullet(30);
                clear();
            }
        }
        if (x>=1050) {
            clear();
        }
    }

    /**
     * clear the bullet
     */
    private void clear() {
        die = true;
    }

    /**
     * action method of bullet
     * @param elements of game
     * @throws IOException
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        if(!die) {
            ArrayList<Zombie> zombies = new ArrayList<>();
            for (Element element : elements)
                if (element instanceof Zombie) {
                    zombies.add((Zombie) element);
                }
            flyAction(zombies);
        }
        else
            elements.remove(this);
    }
}
