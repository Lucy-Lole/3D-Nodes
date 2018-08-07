import java.util.ArrayList;

public class NodeList {
    public ArrayList<Node> Nodes;

    public NodeList() {
        this.Nodes = new ArrayList<Node>();
    }

    public void AddNode(double[] position, double size, double sizeModifier, double[] speed) {
        Nodes.add(new Node(position, size, sizeModifier, speed));
    }

    public void removeNode(){
        //Do a method to remove, prehaps match the current pos of the mouse to pos of any nodes
        //in the array?

    }
}
