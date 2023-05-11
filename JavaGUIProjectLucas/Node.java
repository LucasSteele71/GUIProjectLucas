public class Node{

    Node parent;
    int col;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;
    
    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }

    //Sets the node as a start, goal, or solid node for the algoritim
    public void setAsStart(){
        start = true;
    }

    public void setAsGoal(){
        goal = true;
    }

    public void setAsSolid(){
        solid = true;
    }

    //Sets the nodes as opened or checked for the search method and the arraylists
    public void setAsOpen(){
        open = true;
    }
    public void setAsChecked(){
        checked = true;
    }
}