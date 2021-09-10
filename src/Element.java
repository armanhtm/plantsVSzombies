import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * element abstract class
 * @author Arman Hatami
 * @version 1.0
 */
public abstract class Element implements Serializable {
    //x position
    int x;
    //y position
    int y;
    //height of element
    int height;
    //width of element
    int width;
    //address of image
    String address;
    /**
     * constructor method
     */
    public Element(int x,int y,int height,int width,String address){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.address = address;
    }
    /**
     * get height element
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * get width of element
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * get x position of element
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     * get y position of element
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     * get image of element
     * @return address of image
     */
    public String getImage(){
        return address;
    }

    /**
     * abstract action method for all elements
     * @param elements
     * @throws IOException
     */
    public abstract void Action(ArrayList<Element> elements) throws IOException;
}
