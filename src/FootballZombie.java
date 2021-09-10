import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * football zombie
 */
public class FootballZombie extends Zombie{
    //speed of zombie
    private int speed = 1;
    //live of zombie
    private int live = 1500;

    /**
     * constructor method
     * @param height
     * @param width
     */
    public FootballZombie(int height, int width) {
        super(height, width,"assets/images/zombie_football.gif");
    }

    @Override
    /**
     * action method of zombie
     */
    public void Action(ArrayList<Element> elements) throws IOException {
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
            if (this.updatedTime > this.currentTime + 1000)
                elements.remove(this);
            else
                this.address = "assets/images/zombie_football_dying.gif";
        }
    }

    @Override
    /**
     * empty method because football zombie will not be effected by icy bullet
     */
    public void getIcyBullet() {

    }

    @Override
    /**
     * move method of zombie
     */
    public void move() {
        if(currentTime % 3 == 0)
            this.x -= speed;
        this.speed = 1;
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

