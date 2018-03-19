import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;

public class SMA {
	private final static int memory = 8;
	private int used = 1;
	private long count = 0;
	private Node root;
	private int numGenerated = 0;
	private int numGeneratedTested = 0;
	private ArrayList<String> solution;

	public ArrayList<String> getSolution() {
		return solution;
	}

	public void setSolution(ArrayList<String> solution) {
		this.solution = solution;
	}

	// Hashtable<String, Integer> age;

	public SMA(Node x) {
		solution = new ArrayList<String>();
		root = x;
		root.setF(Heuristic(root.getState()));
		if (root.getF() == 0) {
			System.out.println(root.getState().getState());

			System.exit(0);
		}
		// age = new Hashtable<String, Integer>();
	}

	public void Search(Node init) {
		while (true) {
			// setAge(init);
			numGenerated++;
			Node best = successor(init);
			if (best.getCount() == 0) {
				if (best.getGoal() == 1) {
					StringBuilder genereate = new StringBuilder(root.getState()
							.getState());
					System.out.println(root.getState().getState());
					solution.add(root.getState().getState());
					for (int i = 0; i < best.getPath().length();) {
						int digit, distance = 0;
						digit = Integer.parseInt(root.getPath().charAt(i++)
								+ "");
						if (root.getPath().charAt(i) == '-') {
							StringBuilder sb = new StringBuilder();
							sb.append("-");
							i++;
							sb.append(root.getPath().charAt(i++));
							distance = Integer.parseInt(sb.toString());
						} else
							distance = Integer.parseInt(root.getPath().charAt(
									i++)
									+ "");
						int oldVal = Integer.parseInt(genereate.charAt(digit)
								+ "");
						int newVal = oldVal + distance;
						genereate.setCharAt(digit, (char) (newVal + '0'));
						System.out.println(genereate.toString());
						solution.add(genereate.toString());
					}
					for (int i = 0; i < solution.size() / 2; i++) {
						String temp = new String(solution.get(i));
						solution.set(i, solution.get(solution.size() - 1 - i));
						solution.set(solution.size() - 1 - i, temp);
					}
					System.out.println("Tested:" + numGenerated);
					System.out.println("gernated not Tested:"
							+ (numGeneratedTested - numGenerated));
					break;
				}
				if (best.getGoal() == 1000) {
					System.out.println("Not enough memory!");
					System.out.println("Generate:" + numGenerated);
					System.out.println("Tested not generated:"
							+ (numGeneratedTested - numGenerated));
					System.exit(0);
				}
			}
			int exist = 1;
			while (best.children.size() != 0 && exist == 1) {
				exist = 0;
				for (int i = 0; i < best.children.size(); i++)
					if (best.getF() == best.children.get(i).getF()) {
						best = best.children.get(i);
						exist = 1;
						break;
					}
			}
			if (exist == 1) {
				init = best;
			} else {
				int depth = best.getDepth() + 1, digit = 0;
				StringBuilder m;
				int flag = 0;
				for (int i = 0; i < (8 - best.getDigit()); i++) {
					if (flag == 1)
						break;
					int j = (best.getDigit()) + i;
					for (int k = 1; k <= 8; k++) {
						if (best.getState().getState().charAt(j) != (char) (k + '0')) {
							m = new StringBuilder(best.getState().getState());

							m.setCharAt(j, (char) (k + '0'));
							// if(best.getFstate()==null)
							// System.out.println(best.getState().getState());
							if (m.toString()
									.equals(best.getFstate().getState())) {
								digit = j + 1;
								flag = 1;
								break;
							}
						}
					}
				}
				if (used == memory) {
					removeWorstLeaf(best);
					used--;
				}
				Node node = new Node(best.getFstate(), digit, depth);
				node.setParent(best);
				count++;
				node.setCount(count);
				int temp = Heuristic(node.getState());
				node.setDistance(distance(node, best) + best.getDistance());
				temp += node.getDistance();
				// test
				// if(age.get(node.getState().getState())!=null)
				// temp+=age.get(node.getState().getState());
				//
				node.setF(temp);
				node.setGoal(0);
				best.children.add(node);
				used++;
				init = node;
			}
		}
	}

	// test
	// private void setAge(Node init) {
	// if (age.get(init.getState().getState()) == null)
	// age.put(init.getState().getState(), 1);
	// else
	// age.put(init.getState().getState(),
	// age.get(init.getState().getState()) + 1);
	// }
	//
	public Node successor(Node init) {
		for (int i = 0; i < (8 - init.getDigit()); i++) {

			int j = (init.getDigit()) + i;
			for (int k = 1; k <= 8; k++) {
				if (init.getState().getState().charAt(j) != (char) (k + '0')) {
					numGeneratedTested++;
					StringBuilder m = new StringBuilder(init.getState()
							.getState());
					m.setCharAt(j, (char) (k + '0'));
					if (used == memory) {
						removeWorstLeaf(init);
						used--;
					}

					Node node = new Node(new State(m.toString()), j + 1,
							init.getDepth() + 1);
					node.setParent(init);
					count++;
					node.setCount(count);
					int temp = Heuristic(node.getState());
					if (temp == 0) {
						node.setGoal(1);
						node.setDistance(distance(node, init)
								+ init.getDistance());
						temp += node.getDistance();
						node.setF(temp);

						// extractPath(node);

					} else if (node.getDigit() == 8
							|| node.getDepth() == memory - 1) {
						node.setF(1000);
						node.setGoal(1000);
					} else {
						node.setDistance(distance(node, init)
								+ init.getDistance());
						temp += node.getDistance();
						// test
						// if(age.get(node.getState().getState())!=null)
						// temp+=age.get(node.getState().getState());
						//
						node.setF(temp);
					}

					init.children.add(node);
					used++;

				}

			}
		}
		// path=new ArrayList<String>();
		return edit(init);/*
						 * take best f from childs and forgetten and whteter its
						 * goal or not change its parrent values considiring
						 * best childs and forgotten return last unchanged node
						 */

	}

	private int distance(Node node, Node init) {
		int s1 = Integer.parseInt(node.getState().getState());
		int s2 = Integer.parseInt(init.getState().getState());
		int ans = Math.abs(s1 - s2);
		while (ans >= 10)
			ans /= 10;
		return ans;
	}

	private Node edit(Node init) {
		State s = new State(null);
		State cp = new State(null);
		int temp = init.getF();
		State fstate = null;
		int minc = init.getChildren().get(0).getF();
		int gc = init.getChildren().get(0).getGoal();
		cp.setState(init.getChildren().get(0).getState().getState());
		for (int i = 1; i < init.getChildren().size(); i++)
			if (minc > init.getChildren().get(i).getF()) {
				minc = init.getChildren().get(i).getF();
				gc = init.getChildren().get(i).getGoal();
				cp.setState(init.getChildren().get(i).getState().getState());
			}
		int flag = 0, minf = 0, index = 0, gf = 0;
		if (init.forgotten.size() != 0) {
			flag = 1;
			minf = init.forgotten.get(0);
			gf = init.forgottenGoal.get(0);
			fstate = init.forgottenState.get(0);
			for (int i = 1; i < init.forgotten.size(); i++) {
				if (minf > init.forgotten.get(i)) {
					minf = init.forgotten.get(i);
					gf = init.forgottenGoal.get(i);
					fstate = init.forgottenState.get(i);
					index = i;
				}
			}
		}
		if (flag == 1) {
			if (minf < minc) {
				init.setF(minf);
				init.setGoal(gf);
				init.setFstate(fstate);
				s.setState(fstate.getState());
				init.forgotten.remove(index);
				init.forgottenGoal.remove(index);
				init.forgottenState.remove(index);
			} else {
				init.setF(minc);
				init.setGoal(gc);
				s.setState(cp.getState());
			}
		} else {
			init.setF(minc);
			init.setGoal(gc);
			s.setState(cp.getState());
		}
		if (init.getParent() == null
				|| (init.getF() == temp && init.getGoal() == 0)) {
			if (init.getParent() == null && init.getGoal() == 1
					&& init.getPath() == null)
				init.setPath((difference(s, init.getState())));
			return init;
		} else {
			if (init.getGoal() == 1
					&& init.getF() < init.getParent().getfPath()) {
				String c1 = difference(init.getState(), init.getParent()
						.getState());
				StringBuilder sb = new StringBuilder();
				sb.append(c1);
				if (init.getPath() == null)
					sb.append(difference(s, init.getState()));
				else
					sb.append(init.getPath());

				init.getParent().setPath(sb.toString());
			}
			return edit(init.getParent());
		}
	}

	private String difference(State init, State parent) {
		int s1 = Integer.parseInt(init.getState());
		int s2 = Integer.parseInt(parent.getState());
		int s = s1 - s2;
		int digit = 0;
		while (Math.abs(s) > 9) {
			digit++;
			s /= 10;
		}
		digit = 7 - digit;
		StringBuilder sb = new StringBuilder();
		sb.append(digit);
		sb.append(s);
		return sb.toString();
	}

	public void removeWorstLeaf(Node init) {
		Node worst = worstLeaf(root, null);
		int flag = 0, index = 0;
		if (worst.getParent().getCount() == init.getCount()) {
			flag = 1;
		}
		if (flag == 1) {
			for (int i = 0; i < init.children.size(); i++) {
				if (worst.getState().getState()
						.equals(init.children.get(i).getState().getState())) {

					index = i;
					break;
				}
			}
			worst.getParent().forgotten.add(worst.getF());
			worst.getParent().forgottenGoal.add(worst.getGoal());
			worst.getParent().forgottenState.add(worst.getState());
			worst.setParent(null);
			init.children.remove(index);
			worst = null;
		} else if (worst.getParent().children.size() == 1
				&& worst.getParent().getF() == worst.getF()) {
			worst.getParent().setFstate(worst.getState());
			Node temp = worst.getParent();
			worst.setParent(null);
			temp.children.remove(0);
			worst = null;
		}

		else {
			worst.getParent().forgotten.add(worst.getF());
			worst.getParent().forgottenGoal.add(worst.getGoal());
			worst.getParent().forgottenState.add(worst.getState());
			for (int i = 0; i < worst.getParent().children.size(); i++)
				if (worst
						.getState()
						.getState()
						.equals(worst.getParent().children.get(i).getState()
								.getState())) {
					Node temp = worst.getParent();
					worst.setParent(null);
					temp.children.remove(i);
					worst = null;
					break;
				}
		}

	}

	public static Node worstLeaf(Node x, Node max) {
		if (x.children.size() == 0) {
			if (max == null)
				return x;
			else if (x.getF() == max.getF()) {
				if (x.getCount() < max.getCount())
					return x;
				else
					return max;
			} else if (x.getF() > max.getF())
				return x;
			else
				return max;
		} else {
			Node goal = worstLeaf(x.children.get(0), max);
			for (int i = 1; i < x.children.size(); i++) {
				goal = worstLeaf(x.children.get(i), goal);
			}
			return goal;
		}

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
