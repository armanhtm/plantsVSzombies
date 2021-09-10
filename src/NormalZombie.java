import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * normal zombie
 */
public class NormalZombie extends Zombie{
    //speed of zombie
    private int speed = 2;
    //live of zombie
    private int live = 200;

    /**
     * constructor method
     *
     * @param height
     * @param width
     */
    public NormalZombie(int height,int width) {
        super(height,width,"assets/images/zombie_normal.gif");
    }

    @Override
    /**
     * action method of normal zombie
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        if (!Burnt) {
            if (this.alive) {
                this.currentTime = System.currentTimeMillis();
            }
            Plant plant = meetPlants(elements);
            if (plant != null) {
                if (currentTime % 20 == 0)
                    plant.subLife(5);
                eat();
            } else
                move();
            if (!alive) {
                this.updatedTime = System.currentTimeMillis();
                if (this.updatedTime > this.currentTime + 1200)
                    elements.remove(this);
                else
                    this.address = "assets/images/normalZombiedie.gif";
            }
        }
    }

    @Override
    /**
     * check if zombie hit by icy bullet
     */
    public void getIcyBullet() {
        if(!this.IsIcy)
            this.IsIcy = true;
    }

    @Override
    /**
     * move method of zombie
     */
    public void move() {
        if(currentTime % 3 == 0)
            this.x -= speed;
        if(this.IsIcy)
            this.speed = 1;
        else
            this.speed = 2;
    }

    @Override
    /**
     * eat method of zombie
     */
    public void eat() {
        this.speed = 0;
    }

    @Override
    /**
     * check if zombie hit by bullet
     */
    public void hitByBullet(int hit) {
        this.live -= hit;
        if(this.live <= 0)
            this.alive = false;
    }
}

