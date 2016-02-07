import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.SortedMap;

// File loading:
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class Network
{
	private List<Edge> edges = new ArrayList<Edge>();
	private List<Node> nodes = new ArrayList<Node>();
	
	public Network()
	{
		// create an empty network
		// this.nodes and this.edges are set as empty lists (see above)
	}
	
    public void run ()
            throws NetworkException
        {
        	// n1.getName()
//  		String filename = "test.txt";
    	    String filename = "PPInetwork.txt";
	        System.out.print("Reading file: ");
	        System.out.println(filename);
	        try
	        {
	        	readFromFile(new File(filename));
	        }
	        catch (FileNotFoundException e)
	        {
	            e.printStackTrace();
	        }
	       // this.printMessage();
	        
/*    		System.out.println("\n\n");
	    	for (Node each_node : nodes) {
		    		System.out.print("Node: ");
		    		System.out.print(each_node.getName());
	                System.out.print("\tConnections: ");
	            	System.out.println(each_node.getConns());
		    }
*/
 
/*       	System.out.print("\n\nDegree of P32851: ");	    	
        	if (getDegreeByName("P32851") == -1) {
        		try{
                	throw new NetworkException("Node not found in the node list (are you sure it was entered into the network?).");
            	}catch(NetworkException e){
            		// provide error message
            	}
        	}else{
        		System.out.println(getDegreeByName("P32851"));
        	}
*/
        }
    
    public void addEdge(Node node1, Node node2)
    {
    	Edge e = new Edge(node1, node2);
        if (e != null)
        {
            if (! e.hasDuplicate(edges))
            {
            	// not a duplicate PPI
            	edges.add(e);
            	// whether new protein on the graph or not
            	// it's a unique PPI i.e. another connection
            	
            	if (! node1.hasDuplicate(nodes))
        		{
            		// new protein on the graph
            		nodes.add(node1);
            		// node1.addConnection();

            /*
             		System.out.print(node1.getName());
                    System.out.print(" - connections: ");
                	System.out.println(node1.getConns());
        	*/
        		}
            	// else not a duplicate
        		
            	if (! node2.hasDuplicate(nodes))
            	{
            		// new protein on the graph
            		nodes.add(node2);
            		// node2.addConnection();
            /*
            		System.out.print(node2.getName());
                    System.out.print(" - connections: ");
                	System.out.println(node2.getConns());
        	*/
            	}
            	// else not a duplicate
        		
/*            } else {
	 			System.out.print(node1.getName());
	            System.out.print(" + ");
	 			System.out.print(node2.getName());
	            System.out.print(" = a duplicate edge (Skipping). Connections: ");
	         	System.out.print(node1.getConns());
	            System.out.print(" + ");
	         	System.out.println(node2.getConns());
*/
            }
        } else {
        	try{
            	throw new NetworkException("The edge provided was null - please use alphanumeric strings to name each protein (e.g. UniProtKB identifiers).");
        	}catch(NetworkException E){
        		
        	}
        }
    }

    public void printMessage() 
    {
//        System.out.println(edges.get(1).getNodes()[0].getName());
    	System.out.print("Network size: ");
        System.out.print(nodes.size());
    	System.out.print(" nodes, ");
        System.out.print(edges.size());
    	System.out.println(" edges.");
//      System.out.println(edges.get(1).isSelfIntxn());
    }
    
    public void readFromFile(File f) 
        throws FileNotFoundException
    {
    	Scanner s = null;
    	try
        {
    		s = new Scanner(f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } finally {
        	// clear the variables
        	this.edges = new ArrayList<Edge>();
        	this.nodes = new ArrayList<Node>();
        // List<String> node_pair_list = new ArrayList<String>();
        while (s.hasNextLine()) {
        	String current_line = s.nextLine();
        	String[] columns = current_line.split("\t", -1);
        	Node n1 = new Node(columns[0]);
        	Node n2 = new Node(columns[1]);
        	//System.out.print(n1.getName());
        	//System.out.print("\t");
        	//System.out.println(n2.getName());
        	addEdge(n1, n2);
        	// printMessage();
        	// node_pair_list.add(s.next()); // should add more to the network at each iteration
        }   
        s.close();
//        this.text = text;
        }
    }
    
    public List<Node> getNodes()
    {
    	return this.nodes;
	}
    
    public int getDegreeByName(String node_name)
    {
    	int node_degree = -1;
    	for (Node each_node : nodes)
    	{
    		if (node_name.equals(each_node.getName()))
    		{
    			node_degree = each_node.getConns();
    		}
    	}
    	return node_degree;
    }
    
    public double meanDegree()
    {
    	double sum = 0;
    	for (Node each_node : nodes) {
        	sum += each_node.getConns();
    	}
    	double mean_deg = sum/nodes.size();
    	return mean_deg;
    }
    
    public String[] hubValues()
    {
    	int maxDeg = 0; // any node will have degree of at least 1
    	int n = 0; // number of nodes with that degree
    	for (Node each_node : nodes) {
        	if (maxDeg == 0 || each_node.getConns() > maxDeg) {
        		maxDeg = each_node.getConns();
        	} else if (each_node.getConns() == maxDeg) {
        		n += 1;
        	}
    	}
    	String[] hub_values = new String[n];
    	int hub_n = 0;
    	for (Node each_node : nodes) {
        	if (each_node.getConns() == maxDeg) {
        		hub_values[hub_n++] = each_node.getName();
        	}
    	}
    	return hub_values;
    }
    
    protected String printDistribution(SortedMap<String, Integer> distribution)
    {
    	String table_str = "";
        for(String string: distribution.keySet())
        {
            table_str += string + "\t" + distribution.get(string) + "\n";
        }
        return table_str;
    }
    
    public SortedMap<String, Integer> degreeDistribution()
    {
    	SortedMap<String, Integer> degree_freq = new TreeMap<String, Integer>();

    	for (Node each_node : nodes) {
    		String degree = Integer.toString(each_node.getConns());
        	int deg_count = degree_freq.containsKey(degree) ? degree_freq.get(degree) : 0;
        	degree_freq.put(degree, deg_count + 1);
    	}
    	return degree_freq;
    }
    
    public List<Edge> getEdges()
    {
    	return this.edges;
	}
}
