import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * plant abstract class
 */
public abstract class Plant extends Element{
    //time for making next plant
    double waitingTime;
    //cost of plant
    int cost;
    //life 
    int life;

    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     * @param address
     */
    public Plant(int x, int y, int height, int width, String address) {
        super(x, y, height, width, address);
    }

    /**
     * get waiting time
     * @return waiting time
     */
    public double getWaitingTime() {
        return waitingTime;
    }

    /**
     * get cost of plant
     * @return cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * set life of plant
     * @param life
     */
    public void setLife(int life) {
    	this.life = life;
    }

    /**
     * get waiting time
     * @param wait
     */
    public void setWaitingTime(int wait) {
    	this.waitingTime = wait;
    }

    /**
     * get life value
     * @return
     */
    public int getLife() {
    	return life;
    }

    /**
     * set cost of plant
     * @param cost
     */
    public void setCost(int cost) {
    	this.cost = cost;
    }

    /**
     * sub life of plant
     * @param hit
     */
    public void subLife (int hit){
        life -= hit;
    }
}
