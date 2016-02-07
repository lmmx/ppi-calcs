import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


//File loading/writing...:
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;

public class GUI implements ActionListener {
    private int clicks = 0;
    private JLabel label = new JLabel("No data");
    private JLabel edge1_lbl = new JLabel("Enter a protein-protein pair (space-separated):");
    private JLabel edge2_lbl = new JLabel("");
    private JLabel check1_lbl = new JLabel("Enter the identifier of a protein for its degree:");
    private JLabel check2_lbl = new JLabel("");
    private JTextField text1 = new JTextField();
    private JTextField text2 = new JTextField();
    private JTextArea text_area = new JTextArea(20,1);
    private JFrame frame = new JFrame();
    private Network network;

    public GUI(Network network) {
    	
    	this.network = network; 
    	
        // the clickable button
    	uiButton button1 = new uiButton("Load a PPI list (tab-separated)", 1);
        button1.addActionListener(this);
        
        uiButton button2 = new uiButton("Save degree distribution table", 2);
        button2.addActionListener(this);
        uiButton button3 = new uiButton("Add edge", 3); // both text fields must be filled...
        button3.addActionListener(this);
        uiButton button4 = new uiButton("Check degree of a protein", 4);
        button4.addActionListener(this);
        uiButton button5 = new uiButton("Show network statistics", 5);
        button5.addActionListener(this);
        uiButton button6 = new uiButton("Report hubs", 6);
        button6.addActionListener(this);
        uiButton button7 = new uiButton("Report degree distribution", 7);
        button7.addActionListener(this);


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 2));
        panel.add(button1);
        panel.add(button2);
        panel.add(edge1_lbl);
        panel.add(edge2_lbl);
        panel.add(text1);
        panel.add(button3);
        panel.add(check1_lbl);
        panel.add(check2_lbl);
        panel.add(text2);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);
        panel.add(button7);
        
        text_area.setEditable(false);
        
        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panel2.setLayout(new GridLayout(1, 1));
        panel2.add(label);
        
        JPanel panel3 = new JPanel();
        panel3.setBorder(BorderFactory.createEmptyBorder(30, 220, 10, 220));
        panel3.setLayout(new GridLayout(1, 1));
        panel3.add(text_area);

        // display all 3 panels in separate parts of the frame
        frame.add(panel, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("PPI network analysis");
        frame.pack();
        frame.setVisible(true);
    }
    
    public void refreshLabel() {
    	if (network.getNodes().size() < 1) {
    		label.setText("Load a file to view degree distribution statistics");
    	} else {
    		label.setText(network.getNodes().size() + " nodes, " + network.getEdges().size() + " unique PPIs, " + " mean degree: " + network.meanDegree());
    	}
    }
    
    public void refreshTable() {
    	String header_str = "Degree:\tNumber of nodes:" + "\n------------------------------------------------\n";
        text_area.setText( header_str + network.printDistribution(network.degreeDistribution()) );
    }
    
    public void saveTable() {
    	String table_str = network.printDistribution(network.degreeDistribution());
    	
    	// Name file
    	String filename = JOptionPane.showInputDialog("Name this file");
    	
    	// Use save as dialogue
    	JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(filename));
    	    	
       	// show dialog box
   	    int returnVal = fc.showSaveDialog(frame);
        
        BufferedWriter writer;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	try
	        {
                File f = fc.getSelectedFile();
                writer = new BufferedWriter(new FileWriter(fc.getSelectedFile()));
                writer.write(table_str);
                writer.close();
                JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        } else if(returnVal == JFileChooser.CANCEL_OPTION){
            JOptionPane.showMessageDialog(null, "File save has been canceled");
        }
    }
    
    public void loadFile()
    {
         try
         {
         	JFileChooser fc = new JFileChooser();
        	// show dialog box
        	 int returnVal = fc.showOpenDialog(frame);

             if (returnVal == JFileChooser.APPROVE_OPTION) {
                 File f = fc.getSelectedFile();
             	this.network = new Network();
                 network.readFromFile(f);
                 refreshLabel();
             	refreshTable();
             }
             
         }
         catch (FileNotFoundException e)
         {
             e.printStackTrace();
         }
    }
    
    public void acceptEdge() {
    	String input_text = text1.getText();
    	String n1 = input_text.split("\\s+")[0];
    	String n2 = input_text.split("\\s+")[1];
    	Node node1 = new Node(n1);
    	Node node2 = new Node(n2);
    	try {
    		network.addEdge(node1, node2);
    	} catch(Exception ex) {
    		// in case of invalid edge/node entry
    		ex.printStackTrace();
    	}
    	 refreshLabel();
      	refreshTable();
    }
    
    public void reportDegree() {
    	String raw_input_text = text2.getText();
    	String input_text = raw_input_text.trim();
		int deg_result = 0;
    	try {
    		deg_result = network.getDegreeByName(input_text);
    	} catch(Exception ex) {
    		// in case of invalid node entry
    		ex.printStackTrace();
    	}
    	if (deg_result < 1) {
    		label.setText("'" + input_text + "' is not a node on the graph.");
    	} else {
    		label.setText("The degree of " + input_text + " is: " + Integer.toString(deg_result));
    	}
    }

    // process the button clicks
    public void actionPerformed(ActionEvent e) {
        if (((uiButton)e.getSource()).getId() == 1) {
        	loadFile();
        	// refreshLabel();
        } else if (((uiButton)e.getSource()).getId() == 2) {
        	saveTable();
        } else if (((uiButton)e.getSource()).getId() == 3) {
        	acceptEdge();
        } else if (((uiButton)e.getSource()).getId() == 4) {
        	reportDegree();
        } else if (((uiButton)e.getSource()).getId() == 5) {
        	refreshLabel();
        } else if (((uiButton)e.getSource()).getId() == 6) {
        	text_area.setText("The network's hub(s):" + Arrays.toString(network.hubValues()));
        } else if (((uiButton)e.getSource()).getId() == 7) {
        	refreshTable();
        }
    };

}
