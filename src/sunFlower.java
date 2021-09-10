import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * @author Arman Hatami
 * @version 1.0
 * sun flower
 */
public class sunFlower extends Plant implements Serializable{
	//time of making sun
	double makeSunTime;
	//time of last sun made
	double lastSun;
	//update time
	double updatedTime;

	/**
	 * constructor method
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 * @param flag
	 */
	public sunFlower(int x, int y, int height, int width, boolean flag) {
		super(x, y, height, width, "assets/images/sun_flower.gif");
		this.cost = 50;
		this.waitingTime =  7.5;
		this.life = 50;
		if(flag) {
			makeSunTime = 13;
		}
		else
			makeSunTime = 18;
		lastSun = System.currentTimeMillis();
	}

	@Override
	/**
	 * action method of sun flower
	 */
	public void Action(ArrayList<Element> elements) throws IOException {
		makeSun(makeSunTime * 1000,elements);
		
	}

	/**
	 * make sun method
	 * @param reMake
	 * @param elements
	 * @throws IOException
	 */
	public void makeSun(double reMake,ArrayList<Element> elements) throws IOException {
		double currentTime = System.currentTimeMillis();
		if (life > 0) {
			updatedTime = System.currentTimeMillis();
			if (currentTime >= lastSun + reMake) {
				lastSun = currentTime;
				Sun sun = new Sun(getX(), getY(), 0, 0, false);
				sun.Action(elements);
				elements.add(sun);
			}
		}
		else {
			this.address = "assets/images/sun_flower_dying.gif";
			if(currentTime > updatedTime + 1000)
				elements.remove(this);
		}
	}
}
