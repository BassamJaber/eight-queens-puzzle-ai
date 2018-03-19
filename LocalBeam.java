import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LocalBeam {
	
	private ArrayList<String>solution;
	
	public LocalBeam(){
		solution=new ArrayList<String>();
	}
	
	public ArrayList<String> getSolution() {
		return solution;
	}

	public void setSolution(ArrayList<String> solution) {
		this.solution = solution;
	}
	
	int flag=0;
	public void Search(Node init1, Node init2) {
		if (Heuristic(init1.getState()) == 0) {
			System.out.println(init1.getState().getState());
			System.out.println("root is Solution");
			System.exit(0);
		}
		if (Heuristic(init2.getState()) == 0) {
			System.out.println(init2.getState().getState());
			System.out.println("root is Solution");
			System.exit(0);
		}
		
		Queue<Node> q = new LinkedList<Node>();
		q.add(init1);
		q.add(init2);
		
		while (q.size() != 0) {
			Node temp1 = q.remove();
			successor(temp1);
			Node temp2=q.remove();
			successor(temp2);
			Node temp=new Node();
			if(flag==1)break;
			temp.children= new ArrayList<Node>();
			for(int i=0;i<temp1.children.size();i++)
				temp.children.add(temp1.children.get(i));
			
			for(int i=0;i<temp2.children.size();i++)
				temp.children.add(temp2.children.get(i));
			
			sort(temp);
			for (int i = 0; i < temp.children.size(); i++)
				q.add(temp.children.get(i));
		}
	}

	private void sort(Node temp) {
		for (int i = 0; i < temp.children.size() - 1; i++)
			for (int j = 0; j < temp.children.size(); j++) {
				if (temp.children.get(i).getF() > temp.children.get(j).getF()) {
					Node mem = temp.children.get(i);
					temp.children.set(i, temp.getChildren().get(j));
					temp.children.set(j, mem);
				}
			}
	}

	public void successor(Node init) {

		init.children = new ArrayList<Node>();
		for (int i = 0; i < (8 - init.getDigit()); i++) {
			int j = (init.getDigit()) + i;
			for (int k = 1; k <= 8; k++) {

				if (init.getState().getState().charAt(j) != (char) (k + '0')) {
					StringBuilder m = new StringBuilder(init.getState()
							.getState());

					m.setCharAt(j, (char) (k + '0'));
					Node node = new Node(new State(m.toString()), j + 1,
							init.getDepth() + 1);

					int temp = Heuristic(node.getState());
					node.setParent(init);
					if (temp == 0) {
						extractPath(node);
						flag=1;
					} else if (node.getDigit() == 8) {
						node.setF(1000);
						node.setGoal(1000);
					} else {
						node.setDistance(distance(node, init)
								+ init.getDistance());
						temp += node.getDistance();
						node.setF(temp);
					}

					init.children.add(node);

				}

			}
		}

	}

	private int distance(Node node, Node init) {
		int s1 = Integer.parseInt(node.getState().getState());
		int s2 = Integer.parseInt(init.getState().getState());
		int ans = Math.abs(s1 - s2);
		while (ans >= 10)
			ans /= 10;
		return ans;
	}

	public void extractPath(Node goal) {
		System.out.println(goal.getState().getState());
		while (goal.getParent() != null) {
			this.solution.add(goal.getState().getState());
			goal = goal.getParent();	
			System.out.println(goal.getState().getState());
		}
		this.solution.add(goal.getState().getState());
		
	}

	public int Heuristic(State state) {
		/*
		 * Number of attacks on the state
		 */
		int h = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = i + 1; j < 8; j++) {
				if (state.getState().charAt(i) == state.getState().charAt(j)) {
					h++;
				} else if (Math.abs(i - j) == Math.abs(state.getState().charAt(
						i)
						- state.getState().charAt(j))) {
					h++;
				}
			}
		}
		return h;
	}

}