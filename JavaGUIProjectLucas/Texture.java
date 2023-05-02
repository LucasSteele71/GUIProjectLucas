import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Texture {
    public int[] pixels;
    private String loc;
    public final int SIZE;
    public ArrayList<Texture> textures;

    public Texture(String location, int size){
        loc = location;
        SIZE = size;
        pixels = new int[SIZE*SIZE];
        load();
    }

    private void load(){
        try{
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //The texture png files
    public static Texture wood = new Texture("assets/wood.png", 64);
    public static Texture brick = new Texture("assets/Brick.png", 64);
    public static Texture bluestone = new Texture("assets/BlueStone.png", 64);
    public static Texture stone = new Texture("assets/GreyBrick.png", 64);
}
