package us.dontcareabout.realtor.client.util.gf;

public abstract class Task {
	public final String name;

	private TaskFlow flow;

	public Task(String name) {
		this.name = name;
	}

	protected abstract void start();

	void setFlow(TaskFlow flow) {
		this.flow = flow;
	}

	protected void complete() {
		flow.taskDone();
	}
}