import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * @author Arman Hatami
 * @version 1.0
 * zombie abstract
 */
public abstract class Zombie extends Element{
    //alive or dead
    boolean alive;
    //updated time
    double updatedTime;
    //current time of system
    double currentTime;
    //hit by icy bullet or not
    boolean IsIcy;
    //burnt by cherry or jalapeno or not
    boolean Burnt;
    //available positions for zombies
    int[] available = new int[]{50,150,260,375,470};

    /**
     * constructor method
     * @param height
     * @param width
     * @param address
     */
    public Zombie(int height,int width,String address){
        super(0,0,height,width,address);
        this.x = 1060;
        this.y = available[new Random().nextInt(5)] - 30;
        alive = true;
        IsIcy = false;
        Burnt = false;
    }

    /**
     * check if zombie meet plants or not
     * @param elements
     * @return
     */
    public Plant meetPlants(ArrayList<Element> elements){
            for(Element element : elements){
                if(element instanceof Plant && this.x == element.getX() + 30 && this.y + 30 == element.getY()){
                    return (Plant) element;
                }
            }
        return null;
    }

    /**
     * check if zombie hit by icy bullet
     */
    public abstract void getIcyBullet();

    /**
     * get burned by cherry or jalapeno
     */
    public void getBurned(){
            this.y -= 60;
            this.address = "assets/images/burntZombie.gif";
            Burnt = true;
    }

    /**
     * move method of zombie
     */
    public abstract void move();

    /**
     * eat method of zombie
     */
    public abstract void eat();

    /**
     * check hit by bullet
     * @param hit
     */
    public abstract void hitByBullet(int hit);
}
