public class Key extends Interactions{
    public Key(int col, int row) {
        super(col, row);
        //TODO Auto-generated constructor stub
    }

    public void act(Game game, Camera camera){
        int playerX = (int)camera.xPos;
	    int playerY = (int)camera.yPos;

        //If the player is next to the key the key dissappears and the player now has the key
        if(checkTouching(playerY, playerX) == true){
            game.map[col][row] = 0;
			game.hasKey = true;
        }
    }
}
