import java.util.List;
import java.util.Arrays;

public class Edge
{
	Node n1;
	Node n2;
	boolean self_interact;
	
	public Edge(Node node1, Node node2)
    {
    	this.n1 = node1;
    	this.n2 = node2;
    	if (node1.getName().equals(node2.getName()))
        {
            this.self_interact = true;
        } else {
        	this.self_interact = false;
        }
    }
    
    public boolean isSelfIntxn()
    {
    	if (this.self_interact)
    	{
    		return true;
//    		return "Self interaction";
    	}
    	else
    	{
    		return false;
//    		return "Non-self interaction";
    	}
    		
    }
	
    public Node[] getNodes()
    {
        return new Node[]{ this.n1, this.n2 };
    }
    
    public boolean hasDuplicate(List<Edge> edges)
    {
    	// checks the edge against those in a provided list 
    	List<String> ref_names = Arrays.asList(this.n1.getName(), this.n2.getName());
    	
    	for (Edge each_edge : edges) {
    	    if (ref_names.contains(each_edge.n1.getName()))
    	    {
    	    	if ( ref_names.contains(each_edge.n2.getName()) )
    			{
        	    	// self-interactions mean you have to check exactly here to avoid errors
    	    		if ( this.n1.getName().equals(each_edge.n2.getName()) && this.n2.getName().equals(each_edge.n1.getName())
    	    			 || // if the (first and last && last and first) OR (first and first && last and last) are identical
           				 this.n1.getName().equals(each_edge.n1.getName()) && this.n2.getName().equals(each_edge.n2.getName()) )
    	    		{
/*
 // for double checking the duplicates skipped are genuine
 	    	    		System.out.print("Match found: ");
	    	    		System.out.print(each_edge.n1.getName());
	    	    		System.out.print("--");
	    	    		System.out.print(each_edge.n2.getName());
	    	    		System.out.print(" & ");
	    	    		System.out.print(this.n1.getName());
	    	    		System.out.print("--");
	    	    		System.out.println(this.n2.getName());
*/	
	    	    		return true;
    	    		}
    			}
    	    }
    	}
    	return false;
    }
}
