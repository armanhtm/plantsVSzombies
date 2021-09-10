import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * bucket head zombie
 */
public class BucketHeadZombie extends Zombie{
    //speed of zombie
    private int speed;
    //live of zombie
    private int live = 1300;
    //check if game is easy or hard
    private boolean easy;

    /**
     * constructor method
     * @param height height of yard
     * @param width width of yard
     * @param easy easy or hard
     */
    public BucketHeadZombie(int height,int width,boolean easy) {
        super(height,width,"assets/images/bucket1.gif");
        this.easy = easy;
        if(easy)
            speed = 2;
        else
            speed = 3;
    }

    @Override
    /**
     * action method of bucket head zombie
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        if (!Burnt) {
            if (this.alive) {
                this.currentTime = System.currentTimeMillis();
            }
            Plant plant = meetPlants(elements);
            if (plant != null) {
                if (currentTime % 20 == 0)
                    if(easy)
                        plant.subLife(15);
                    else
                        plant.subLife(25);
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
        eat();
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
        if(this.IsIcy && easy)
            this.speed = 1;
        else
            if(this.IsIcy && !easy)
                this.speed = 2;
            else
                if(easy)
                    this.speed = 2;
                else
                    this.speed = 3;
        if(live < 200)
            this.address = "assets/images/zombie_normal.gif";
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
     * hit method of zombie
     */
    public void hitByBullet(int hit) {
        this.live -= hit;
        if(this.live <= 0)
            this.alive = false;
    }
}


