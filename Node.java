import java.util.List;

public class Node
{
	public String name;
	public int connections; // this is the degree: note self-loops increment degree twice
	
    public Node()
    {
        this.name = "";
        // all nodes are added on an edge so initiate at 1 not 0
        this.connections = 1;
    }

    public Node(String node_name)
    {
        this.name = node_name;
        this.connections = 1;
    }

    public String getName()
    {
        return this.name;
    }

    protected void addConnection()
    {
        this.connections += 1;
	/*
        System.out.print(" - connections: ");
    	System.out.println(this.connections); 
	 */
    }
    
    public boolean hasDuplicate(List<Node> nodes)
    {
    	
    	// checks the node against those in a provided list 
    	boolean is_duplicate = false;

    	for (Node each_node : nodes)
    	{
    	    if (this.name.equals(each_node.getName()))
    	    {
    	    	// increment connections on the node in the list
    	    	each_node.addConnection();
    	    	
	    		//System.out.print("Matching node: ");
	    		//System.out.println(each_node.getName());
	    		is_duplicate = true;
    		}
	    }
    	return is_duplicate;
    }
    
    public int getConns()
    {
    	return this.connections;
    }
}
