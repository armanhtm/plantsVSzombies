import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
/**
 * @author Arman Hatami
 * @version 1.0
 * walnut
 */
public class Wallnut extends Plant{
	//system current time
	private double currentTime;
	//half life of walnut
	private int halfLife = 75;

	/**
	 * constructor method
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 */
	public Wallnut(int x, int y, int height, int width){
		super(x, y, height, width, "assets/images/walnut_full_life.gif");
		this.waitingTime = 30;
		this.cost = 50;
		this.life = 150;
	}

	/**
	 * action method of walnut
	 * @param elements
	 * @throws IOException
	 */
	public void Action(ArrayList<Element> elements) throws IOException {
		checkLife(elements);
	}

	/**
	 * check if walnut is still alive or nut
	 * @param elements
	 * @throws IOException
	 */
	public void checkLife(ArrayList<Element> elements) throws IOException {
		if(life < halfLife && life > 0) {
			this.address = "assets/images/half_wallnut.gif";
			currentTime = System.currentTimeMillis();
		}
		if(life < 0) {
			double updatedTime = System.currentTimeMillis();
			if (updatedTime > currentTime + 1800 && updatedTime < currentTime + 2000)
				this.address = "assets/images/die_wallnut.gif";
			if (updatedTime > currentTime + 2000){
				elements.remove(this);
			}
		}
				
	}
}
