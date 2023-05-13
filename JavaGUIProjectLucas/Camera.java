import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Camera implements KeyListener{
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = 0.08;
    public final double ROTATION_SPEED = 0.045;

    /*These variables represent the cameras x and y coordinates on the map the x and y components of the vector 
    * that is the direction the player is facing and the x and y components of the vector perpendicular to the facing direction
    this vectors length is used to represent the maximum fov area
    */
    public Camera(double x, double y, double xd, double yd, double xp, double yp){
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
    }

    //When the key is pressed the camera starts the movement/rotation
    public void keyPressed(KeyEvent key) {
        if((key.getKeyCode() == KeyEvent.VK_LEFT))
            left = true;
        if((key.getKeyCode() == KeyEvent.VK_RIGHT))
            right = true;
        if((key.getKeyCode() == KeyEvent.VK_UP))
            forward = true;
        if((key.getKeyCode() == KeyEvent.VK_DOWN))
            back = true;
    }

    //When the key is released this movement stops
    public void keyReleased(KeyEvent key){
        if((key.getKeyCode() == KeyEvent.VK_LEFT)){
            left = false;
        }
        if((key.getKeyCode() == KeyEvent.VK_RIGHT)){
            right = false;
        }
        if((key.getKeyCode() == KeyEvent.VK_UP)){
            forward = false;
        }
        if((key.getKeyCode() == KeyEvent.VK_DOWN)){
            back = false;
        }
    }

    public void update(int[][] map){
        //If statements to check if the next movement will take the player to open space,
        // if the movement doesn't take the player to open space the movement won't apply 
        // this prevents the player from going into a wall
        if(forward){
            if(map[(int)(xPos + xDir *MOVE_SPEED)][(int)yPos] == 0){
                xPos += xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos + yDir *MOVE_SPEED)] == 0){
                yPos += yDir*MOVE_SPEED;
            }
        }
        if(back){
            if(map[(int)(xPos - xDir *MOVE_SPEED)][(int)yPos] == 0){
                xPos -= xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(yPos - yDir *MOVE_SPEED)] == 0){
                yPos -= yDir*MOVE_SPEED;
            }
        }
        /*This changes the cameras rotation by using a rotation matrix
         * [xcos0 - ysin0]
         * [xsin0 + ycos0]
         * this is applied to the direction vector and the plane vector at a rate of the rotation speed to change the cameras direction
         */
        if(right){
            double oldxDir = xDir;
            xDir = xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
            yDir = oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane = xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
            yPlane = oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
        }
        if(left){
            double oldxDir = xDir;
            xDir = xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
            yDir = oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane = xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
            yPlane = oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
}
