import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable{
    private static final long serialVersionUID = 1L;
	public int keyCol;
	public int keyRow;
	public JFrame parent = new JFrame();
	//The condition to open the door
	public boolean hasKey = false;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    public boolean running;
    private BufferedImage image;
	//Used to randomly place the key throughout the map
	private Random rand = new Random();
    public int[] pixels;
	public ArrayList<Texture> textures;
	private ArrayList<Interactions> actors = new ArrayList<>();
	//This is the 2d instanse of the map where the zeros are open space where the player and enemy can move and the numbers correspond to different textures
    public int[][] map = {
            {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
			{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
			{1,0,3,3,0,3,3,0,0,0,0,0,0,0,2},
			{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,2,2,2,0,2,2,2},
			{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
			{1,0,3,3,0,3,3,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
			{1,4,0,0,4,4,4,0,4,4,0,0,0,4,4},
			{1,4,0,0,0,0,0,0,0,0,0,0,0,0,4},
			{1,4,0,0,1,1,1,4,4,0,0,0,3,0,4},
			{1,0,0,0,0,0,0,0,0,0,0,0,3,0,4},
			{1,0,0,0,0,0,0,3,3,3,3,3,3,0,4},
			{1,4,0,4,1,0,0,0,0,0,0,0,0,0,4},
			{1,1,7,1,1,1,1,4,4,4,4,4,4,4,4}
    };
	//References to all of the objects
	public Camera camera;
	public Screen screen;
	public Pathfinder pathfinder;
	public Enemy enemy;
	public Audio audio;
	public Key key;
	public Door door;

	public Game(){
		thread = new Thread(this);
		//Creates a new buffered image with the dimensions of the screen
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		//Adds all of the textures used to the world
		textures = new ArrayList<Texture>();
        textures.add(Texture.wall);
        textures.add(Texture.brick);
        textures.add(Texture.stripes);
        textures.add(Texture.triangles);
		textures.add(Texture.clown);
		textures.add(Texture.key);
		textures.add(Texture.doorClosed);
		textures.add(Texture.doorOpened);
		//Sets up a basic jframe
		setSize(640, 480);
		setResizable(false);
		setTitle("Theme Park");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		//Creates all of the instances of the objects the game needs
		camera = new Camera(11, 3, 1, 0, 0, -.66);
		pathfinder = new Pathfinder(this, camera);
		addKeyListener(camera);
		enemy = new Enemy(this, camera, 2, 12);
		door = new Door(2, 14);
		this.actors.add(door);
		addKey();
		screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
		//Creates the audio class and starts playing the wav files
		audio = new Audio();
		audio.playAudio("JavaGUIProjectLucas\\Music\\carnival-ambiance-oeteldonk-holland-outdoor-01-19575.wav");
		audio.playAudio("JavaGUIProjectLucas\\Music\\Monster - walk and grunt.wav");
		start();
	}

	private synchronized void start(){
		//Starts the games update loop
		running = true;
		thread.start();
	}
	public synchronized void stop(){
		running = false;
		try{
			thread.join();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}

	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		bs.show();
	}

	//This method is what makes the enemy move
	public void updateMap(){
		//Gets the players current location and sends it to the enemy
		int playerX = (int)camera.xPos;
		int playerY = (int)camera.yPos;
		enemy.searchPath(playerX, playerY, map);
	}

	//Creates a new key object at a random point
	public void addKey(){
		boolean loop = true;
		while(loop){
		keyCol = rand.nextInt(9);
		keyRow = rand.nextInt(15);
		//Checks if the random location is empty space and not a wall
		//If it is the key will be placed and the loop breaks, if not the loop will countinue
		if(map[keyCol][keyRow] == 0){
			map[keyCol][keyRow] = 6;
			key = new Key(keyCol, keyRow);
			this.actors.add(key);
			break;
		}
	}
	}

	public void run(){
		int updateCounter = 0;
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0; //this means it runs 60 times a second
		double delta = 0;
		requestFocus();
		while(running){
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while(delta >= 1){ //makes sure the frames are only updated 60 times a second
				updateCounter++;
				//Makes the enemy move roughly every 0.333 seconds and after checks if the enemy has touched the player
				if(updateCounter == 20){
					updateMap();
					enemy.reachedPlayer();;
					updateCounter = 0;
				}
				//Calls every Interactions subclass to act which just checks if the player is touching them
				for (int i = 0; i < this.actors.size(); i++) {
					this.actors.get(i).act(this, camera);
				}
				//Updates the screen which means it calculates new raycast vectors
				screen.update(camera, pixels);
				//Updates the camera to check for movement
				camera.update(map);
				delta--;
			}
			//renders the screen 
			render();
		}
	}
}

