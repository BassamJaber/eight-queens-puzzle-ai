import java.util.ArrayList;

public class Node {

	private State state;
	private String path;
	private int fPath;
	public int getfPath() {
		return fPath;
	}

	public void setfPath(int fPath) {
		this.fPath = fPath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private int digit;
	private int distance;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	private int f;
	private long count;
	ArrayList<State> forgottenState;
	private State fstate;

	public State getFstate() {
		return fstate;
	}

	public void setFstate(State fstate) {
		this.fstate = fstate;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	private int goal;
	ArrayList<Integer> forgotten;
	ArrayList<Integer> forgottenGoal = new ArrayList<Integer>();
	private int depth;

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	ArrayList<Node> children;
	private Node parent = null;

	public Node() {
		super();
	}

	public Node(State state, int digit, int depth) {
		super();
		forgotten = new ArrayList<Integer>();
		forgottenGoal = new ArrayList<Integer>();
		this.children = new ArrayList<Node>();
		;
		this.state = state;
		this.digit = digit;
		this.parent = null;
		this.depth = depth;
		forgottenState = new ArrayList<State>();
		distance = 0;
		fPath=1000;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}

	public ArrayList<Node> getChildren() {

		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}
