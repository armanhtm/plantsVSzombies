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
 * icy bullet
 */
public class IcyBullet extends Element{
    boolean die;

    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public IcyBullet(int x, int y, int height, int width) throws IOException {
        super(x, y, height, width, "assets/images/freezepea.png");
        die = false;
    }

    /**
     * attack method of icy bullet
     * @param zb
     * @return
     */
    private boolean attack(Zombie zb) {

        if(new Rectangle(x, y, 30, 30).intersects(zb.getX(), zb.getY(), 80, 100)) {
            return true;
        }
        return false;
    }

    /**
     * fly method of icy bullet
     * @param zbList
     */
    private void flyAction(ArrayList<Zombie> zbList) {
        x+=5;
        for(int x=0;x<zbList.size();x++) {
            if(attack(zbList.get(x))) {
                zbList.get(x).getIcyBullet();
                zbList.get(x).hitByBullet(35);
                clear();
            }
        }
        if (x>=1050) {
            clear();
        }
    }

    /**
     * clear of icy bullet
     */
    private void clear() {
        die = true;
    }

    /**
     * action method of icy bullet
     * @param elements
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
