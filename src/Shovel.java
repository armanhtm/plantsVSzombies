import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Arman Hatami
 * @version 1.0
 * shovel
 */
public class Shovel extends Element{
    //x location to remove
    private int XPlant;
    //y location to remove
    private int YPlant;
    /**
     * constructor method
     *
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Shovel(int x, int y, int height, int width) {
        super(x, y, height, width, "assets/images/shovel.png");
        XPlant = 0;
        YPlant = 0;
    }

    @Override
    /**
     * action method of shovel
     */
    public void Action(ArrayList<Element> elements) throws IOException {
        ArrayList<Plant> plants = new ArrayList<>();
        for(Element element : elements)
            if(element instanceof  Plant)
                plants.add((Plant) element);
        for(Plant plant : plants)
            if(plant.getX() == XPlant && plant.getY() == YPlant) {
                elements.remove(plant);
                break;
            }
        this.XPlant = 0;
        this.YPlant = 0;
    }

    /**
     * get location of plant
     * @param XPlant
     * @param YPlant
     */
    public void getPlant(int XPlant, int YPlant){
        this.XPlant = XPlant;
        this.YPlant = YPlant;
    }
}
