import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class EightQueenFrame extends JFrame implements KeyEventDispatcher {

	private MyCanvas canvas;
	private ArrayList<String> list = null;
	private int count = 0;

	private MyCanvas createCanvas() {
		MyCanvas canvas = new MyCanvas();
		canvas.setBackground(Color.BLACK);
		return canvas;
	}

	private LayoutManager createLayout() {
		LayoutManager bl = new BorderLayout();
		return bl;
	}

	protected void frameInit() {
		super.frameInit();
		Container cp = getContentPane();
		cp.setLayout(createLayout());
		canvas = createCanvas();
		cp.add(canvas, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(590, 610);
		setResizable(false);
		setVisible(true);
	}

	public EightQueenFrame(Node x) {

		KeyboardFocusManager manager;
		manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);
		
		System.out.println("1- Simplified Memory A* \n2- Local Beam ");
		Scanner input = new Scanner(System.in);
		System.out.println("Processing");
		
		
		SMA s=null;
		LocalBeam s2=null;
		int c=input.nextInt();
		int flag=0;
		long startTime = System.currentTimeMillis();
		while(flag==0){
		if(c==1){
			flag=1;
		    s = new SMA(x);
			s.Search(x);
		}else if(c==2){
			flag=2;
			String state= JOptionPane.showInputDialog ( "Enter another Initial State" );
	    	Node x1 = new Node(new State(state), 0, 0);
		    s2=new LocalBeam();
			s2.Search(x,x1);
		}
		}
		
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("\n\nExecution Time (MilliSeconds) " + elapsedTime
				+ " ms");
		
		Stack<String> stack = new Stack<String>();
		ArrayList<String> l =(c==1)? s.getSolution():s2.getSolution();
		list = new ArrayList<String>();
		
		for (int i = 0; i < l.size(); i++) {
			stack.push(l.get(i));
		}
		while (!stack.isEmpty()) {
			list.add(stack.pop());
		}

	}

	/**
	 * This is the method from the KeyEventDispatcher interface. The code is
	 * called whenever a key is pressed. It checks for up arrow and down arrow.
	 * These keys are used for navigating through the solutions. If any of these
	 * keys are pressed then it notifies the halted thread to proceed.
	 * 
	 */
	public synchronized boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getKeyCode() == 38 && e.getID() == 402) {// When the up arrow is
														// released
			if (count >= 1)
				count--;
			notifyAll();
		}
		if (e.getKeyCode() == 40 && e.getID() == 402) {// When the down arrow is
														// released
			count++;
			notifyAll();
		}
		return false;
	}

	/**
	 * Method to draw the queens on the board. This method just sets the value
	 * of the board and calls repaint of the canvas.Then it waits until it
	 * receives notification to proceed.
	 */

	public synchronized void display() {
		while (count < list.size()) {
			try {
				canvas.setSolution((String) list.get(count));
				setTitle("Eight Queen : Step  " + (count + 1));
				canvas.repaint();
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}