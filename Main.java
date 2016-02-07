
public class Main {
	
	private Node n1 = new Node("Hello");
	private Node n2 = new Node("world");
	private Edge e = new Edge(n1, n2);
	
	// starting block for the entire program, passes to the Network class to run
	// (non-static variable cannot be referenced from a static context)
	
    public static void main(String[] args) {
        try
        {
        	Network network = new Network();
        	network.run();
        	GUI gui = new GUI(network);
        	gui.refreshLabel();
        	gui.refreshTable();
        	// network.printMessage();
        	// System.out.print("Mean degree: "); System.out.println(network.meanDegree());
        	// System.out.print("Hubs: "); System.out.println();
        	
        }
        catch (NetworkException e)
        {
            e.printStackTrace();
        }
    }
    
}