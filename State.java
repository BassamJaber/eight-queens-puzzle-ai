
public class State {

	private String state;

	public State(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isGoal() {
		int len = this.state.length();
		if (this.state.contains("" + 0)) {
			return false;
		} else {
			for (int i = 0; i < len - 1; i++) {
				for (int j = i + 1; j < len; j++) {
					if (this.state.charAt(i) == this.state.charAt(j)) {
						return false;
					} else if (Math.abs(i - j) == Math.abs(this.state.charAt(i)
							- this.state.charAt(j))) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
