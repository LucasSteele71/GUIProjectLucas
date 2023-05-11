import javax.swing.JOptionPane;

public class Door extends Interactions {
    public Door(int col, int row) {
        super(col, row);
        //TODO Auto-generated constructor stub
    }

    public void act(Game game, Camera camera){
        //If the player has got the key the door will switch to an open sprite
        if(game.hasKey){
            game.map[row][col] = 8;
        }
        int playerX = (int)camera.xPos;
	    int playerY = (int)camera.yPos;
        //If the player has the key and is touching the door the game will stop running and bring up a pop up window
        if(checkTouching(playerX, playerY) == true && game.hasKey){
            JOptionPane.showMessageDialog(game.parent, "You Win!", "Win", JOptionPane.PLAIN_MESSAGE);
			game.stop();
        }
    }
}
