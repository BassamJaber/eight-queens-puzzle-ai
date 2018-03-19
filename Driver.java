import javax.swing.JOptionPane;


public class Driver {
    public static void main(String[] args) throws Exception {  
    	
    	String state= JOptionPane.showInputDialog ( "Enter any Initial State" );
    	Node x = new Node(new State(state), 0, 0);
    	
        EightQueenFrame queenFrame = new EightQueenFrame(x);  
        queenFrame.display();  
        JOptionPane.showMessageDialog(null, "You found a Solution ! ");
        
		System.exit(0);
    }  
}
