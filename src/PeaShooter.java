import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * pea shooter
 */
public class PeaShooter extends Plant{
    //current time of system
    double currentTime;
    //zombies are coming or not
    boolean areComing;
    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public PeaShooter(int x, int y, int height, int width) {
        super(x, y, height, width, "assets/images/pea_shooter.gif");
        this.life = 70;
        this.cost = 100;
        this.waitingTime = 7.5;
        currentTime = System.currentTimeMillis();
    }

    /**
     * set zombie is coming
     * @param zombie
     */
    public void zombieIsComing(boolean zombie){
        areComing = zombie;
    }

    @Override
    /**
     * action method of pea shooter
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        double updatedTime = System.currentTimeMillis();
        checkZombies(elements);
        if(life <= 0){
            if (updatedTime > currentTime + 1100 && updatedTime < currentTime + 2000)
                this.address = "assets/images/pea_shooter_dying.gif";
            if(updatedTime > currentTime + 2000)
                elements.remove(this);
        }
        if (updatedTime > currentTime + 2000 && life > 0){
            currentTime = updatedTime;
            if (areComing){
                shoot(elements);
            }
        }
    }

    /**
     * shoot method of pea shooter
     * @param elements
     * @throws IOException
     */
    public void shoot(ArrayList<Element> elements) throws IOException {
        Bullet bullet = new Bullet(getX() + 60,getY() + 10,0,0);
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
            if(zombie.getY() + 30 == this.y && zombie.getX() >= this.x) {
                areComing = true;
                return;
            }
        areComing = false;
    }
}
