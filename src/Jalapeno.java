import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * jalapeno plant
 */
public class Jalapeno extends Plant{
    //current time of system
    double currentTime;
    //live or die
    boolean die;
    //list of zombies
    ArrayList<Zombie> zombies;
    //list of burned zombies
    ArrayList<Zombie> BurnedZombies;
    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Jalapeno(int x, int y, int height, int width) {
        super(x, y, height, width, "assets/images/jalapeno_4.gif");
        this.cost = 125;
        this.life = 70;
        die = false;
        this.waitingTime = 30;
        this.currentTime = System.currentTimeMillis();
    }

    @Override
    /**
     * action method of jalapeno
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        double updatedTime = System.currentTimeMillis();
        if(!die && updatedTime - currentTime < 1000){
            zombies = new ArrayList<>();
            BurnedZombies = new ArrayList<>();
            for (Element element : elements) {
                if (element instanceof NormalZombie || element instanceof ConeHeadZombie || element instanceof BucketHeadZombie)
                    zombies.add((Zombie) element);
            }
            for (Zombie zombie : zombies) {
                if (zombie.getY() + 30 == this.y) {
                    BurnedZombies.add(zombie);
                }
            }
        }
        if(!die && updatedTime - currentTime > 1000){
            this.address = "assets/images/jalapenoFire.gif";
            for(Zombie zombie : BurnedZombies)
                zombie.getBurned();
            die = true;
        }
        if(die && updatedTime - currentTime > 2500){
            for(Zombie zombie : BurnedZombies) {
                elements.remove(zombie);
            }
            elements.remove(this);
        }
    }
}

