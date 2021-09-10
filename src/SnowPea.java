import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * snow pea shooter
 */
public class SnowPea extends Plant{
    //current time of system
    double currentTime;
    //check if zombies are coming
    boolean areComing;
    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public SnowPea(int x, int y, int height, int width,boolean flag) {
        super(x, y, height, width, "assets/images/freezepeashooter.gif");
        this.life = 100;
        this.cost = 175;
        if(flag)
            this.waitingTime = 7.5;
        else
            this.waitingTime = 30;
        currentTime = System.currentTimeMillis();
    }

    /**
     * set zombies are coming
     * @param zombie
     */
    public void zombieIsComing(boolean zombie){
        areComing = zombie;
    }

    @Override
    /**
     * action method of snow pea
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        double updatedTime = System.currentTimeMillis();
        checkZombies(elements);
        if(life < 0){
            if (updatedTime > currentTime + 1900 && updatedTime < currentTime + 2000)
                this.address = "assets/images/die_snowpea.gif";
            if(updatedTime > currentTime + 2000)
                elements.remove(this);
        }
        if (life > 0 && updatedTime > currentTime + 2000){
            currentTime = updatedTime;
            if (areComing){
                shoot(elements);
            }
        }
    }

    /**
     * shoot method of snow pea
     * @param elements
     * @throws IOException
     */
    public void shoot(ArrayList<Element> elements) throws IOException {
        IcyBullet bullet = new IcyBullet(getX() + 60 ,getY() + 10,0,0);
        elements.add(bullet);
    }

    /**
     * check if zombies are coming
     * @param elements
     */
    public void checkZombies(ArrayList<Element> elements){
        ArrayList<Zombie> zombies = new ArrayList<>();
        for(Element element : elements)
            if(element instanceof Zombie)
                zombies.add((Zombie) element);
        for(Zombie zombie : zombies)
            if(zombie.getY() + 30 == this.y) {
                areComing = true;
                return;
            }
        areComing = false;
    }
}
