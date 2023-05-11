import javax.swing.JOptionPane;

public class Enemy {
    Game game;
    Camera camera;

    int startCol;
    int startRow;

    public Enemy(Game game, Camera camera, int startCol, int startRow){
        this.startCol = startCol;
        this.startRow = startRow;
        this.game = game;
        this.camera = camera;
    }

    //If the enemy has just moved into a node the player currently occupies the game will stop running and display a popup
    public void reachedPlayer(){
        int playerX = (int)camera.xPos;
	    int playerY = (int)camera.yPos;
        if(playerX == startCol && playerY == startRow){
			JOptionPane.showMessageDialog(game.parent, "You Lose.","Lost", JOptionPane.PLAIN_MESSAGE);
			game.stop();
		}
    }
    

    // Everytime this method is called the enemy takes its current position as the start node and the players 
    // current position as the goal node and passes this information to the pathfinding algoritim
    
    public int[][] searchPath(int playerCol, int playerRow, int[][] map){
        game.pathfinder.setNodes(startCol, startRow, playerCol, playerRow);

        // The map is setup so that the enmey should always be able to find a path but if not nothing will happen and the game countinues
        if(game.pathfinder.search() == false){
            return map;
        }
            //After finding the best path to the player the enemy sets its last position on the map to a 0
            //and then sets its current position to the next node in the pathlist, it then returns the map with these changes
            int nextCol = game.pathfinder.pathList.get(0).col;
            int nextRow = game.pathfinder.pathList.get(0).row;
            game.map[startCol][startRow] = 0;
            startCol = nextCol;
            startRow = nextRow;
            game.map[nextCol][nextRow] = 5;
            return map;
    }
}
