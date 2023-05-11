public class Interactions {
    public Camera camera;

    int col;
    int row;
    public Interactions(int col, int row){
        this.col = col;
        this.row = row;

    }

    //The checkTouching method checks to see if the players current position is next to the square the object is in
    protected boolean checkTouching(int playerX, int playerY){
        if((playerX == row || playerX == (row + 1) || playerX == (row - 1)) && (playerY == col || playerY == (col + 1) || playerY == (col - 1))){
			return true;
		}
        else{
            return false;
        }
    }
    //Creates the act method for all of this classes children
    public void act(Game game, Camera camera){
        
    }
}
