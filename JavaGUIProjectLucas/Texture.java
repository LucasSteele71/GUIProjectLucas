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
    public static Texture wall = new Texture("JavaGUIProjectLucas\\assets\\CircusWall.png", 64);
    public static Texture key = new Texture("JavaGUIProjectLucas/assets/Key.png", 64);
    public static Texture doorClosed = new Texture("JavaGUIProjectLucas/assets/DoorClosed.png", 64);
    public static Texture doorOpened = new Texture("JavaGUIProjectLucas/assets/DoorOpened.png", 64);
    public static Texture clown = new Texture("JavaGUIProjectLucas/assets/ScaryClown (1).png", 64);
    public static Texture brick = new Texture("JavaGUIProjectLucas/assets/Brick.png", 64);
    public static Texture stripes = new Texture("JavaGUIProjectLucas\\assets\\Stripes.png", 64);
    public static Texture triangles = new Texture("JavaGUIProjectLucas\\assets\\Triangles.png", 64);
}
