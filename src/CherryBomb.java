import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * cherry bomb
 */
public class CherryBomb extends Plant{
    //current time
    double currentTime;
    //live or die
    boolean die;
    //list of zombies
    ArrayList<Zombie> zombies;
    //list of burned zombies
    ArrayList<Zombie> BurnedZombies;
    //flag for inserting gif
    boolean flag;
    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public CherryBomb(int x, int y, int height, int width,boolean flag) {
        super(x, y, height, width, "assets/images/anim_cherrybomb.gif");
        this.x -= 90;
        this.y -= 65;
        this.cost = 150;
        this.life = 70;
        this.die = false;
        this.flag = true;
        if(flag)
            this.waitingTime = 30;
        else
            this.waitingTime = 45;
        this.currentTime = System.currentTimeMillis();
        this.address = "assets/images/cherry.gif";
    }

    @Override
    /**
     * action method of cherry bomb
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        if(flag) {
            this.address = "assets/images/cherry.gif";
            flag = false;
        }
        double updatedTime = System.currentTimeMillis();
        if(!die && updatedTime - currentTime < 1000){
            zombies = new ArrayList<>();
            BurnedZombies = new ArrayList<>();
            for (Element element : elements) {
                if (element instanceof NormalZombie || element instanceof ConeHeadZombie || element instanceof BucketHeadZombie)
                    zombies.add((Zombie) element);
            }
            for (Zombie zombie : zombies) {
                if (new Rectangle(this.x, this.y , 300, 300).intersects(zombie.x , zombie.y + 30, 100, 100)) {
                    BurnedZombies.add(zombie);
                }
            }
        }
        if(!die && updatedTime - currentTime > 1000){
            for(Zombie zombie : BurnedZombies)
                zombie.getBurned();
            die = true;
        }
        if(die && updatedTime - currentTime > 2000){
             for(Zombie zombie : BurnedZombies) {
                 elements.remove(zombie);
             }
             elements.remove(this);
        }
    }
}

