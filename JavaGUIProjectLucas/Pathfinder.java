import java.util.ArrayList;
import java.util.Collections;

public class Pathfinder {
    Game game;
    Camera camera;
    
    Node[][] node;
    // These three nodes represent the objects start position and its end goal
    Node startNode, goalNode, currentNode;
    //These arraylist are used to sort organise checked and not checked nodes for the algoritim a
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();
    //The sequence of nodes that make the best path to the goal are added to this list
    ArrayList<Node> pathList = new ArrayList<>();

    //Lets the algoritim know when to stop and the step is a check to make sure the algoritim does not keep trying for eternity if a path cannot be found
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(Game game, Camera camera){
        this.game = game;
        instantiateNodes();
    }
    public void instantiateNodes(){
        node = new Node[game.mapWidth][game.mapHeight];

        int col = 0;
        int row = 0;

        //Breaks the map into a grid of nodes
        while(col < game.mapWidth && row < game.mapHeight){
            node[col][row] = new Node(col, row);
            col++;
            if(col == game.mapWidth){
                col = 0;
                row++;
            }
        }
    }

    //Loops through the entire array of nodes and resets there values
    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < game.mapWidth && row < game.mapHeight) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == game.mapWidth) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        checkedList.clear();
        goalReached = false;
        step = 0;
        
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        //Loops through the maps width and height and checks if it has a number value which indicates a texture
        //if there is a texture in that tile the pathfinder will set it as solid which means that nodes has no value and the
        // algoritim will ignore it, this also means the enemy won't walk through walls
        while(col < game.mapWidth && row < game.mapHeight){
            if(game.map[col][row] > 0 && game.map[col][row] != 5){
                node[col][row].solid = true;
            } else{
                // Sets the cost on the nodes
                getCost(node[col][row]);
            }
            col++;
            if(col == game.mapWidth){
                col = 0;
                row++;
            }
        }
    }

    //A* is f = g + h
    //Where g is the distance from the current node to the starting node and h is the distance from the current node to the goal
    public void getCost(Node node) {
        // Get G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // Get H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // Get F cost
        node.fCost = node.gCost + node.hCost;
    }

    //The actual algoritim method
    public boolean search() {
        //Creates a while loop that keeps running until the path to the goal is found or the loop lasts too long
        while (goalReached == false && step < 300) {
            int col = currentNode.col;
            int row = currentNode.row;

            //Sets the node as checked
            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            // Open the up node
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            // Open the left node
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            // Open the down node
            if (row + 1 < game.mapHeight) {
                openNode(node[col][row + 1]);
            }
            // Open the right node
            if (col + 1 < game.mapWidth) {
                openNode(node[col + 1][row]);
            }

            //Creates an index to store the best values of the nodes cost values to add the best to the path later
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            //If every node has been checked break the loop
            if(openList.size() == 0){
                break;
            }

            //Checks if the current node is the goal node and if so the goal has been reached and we can track the path
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node) {
        //if the nodes has not already been checked or if it is not solid we will set as open and add it to the checked list so it is not checked again
        if (node.open == false && node.checked == false && node.solid == false) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {   
        Node current = goalNode;

        //Starts at the goal node and follows the path down until it reaches the start node
        while (current != startNode) {
            pathList.add(current);
            current = current.parent;

            if (current != startNode) {
                pathList.add(current);
            }
        }
        //Reverses the pathlist to get the best node right next to the enemy
        Collections.reverse(pathList);
    }
}
