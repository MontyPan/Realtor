package us.dontcareabout.realtor.client.util.gf;

public abstract class Task {
	private TaskFlow flow;

	protected abstract void start();

	void setFlow(TaskFlow flow) {
		this.flow = flow;
	}

	protected void complete() {
		flow.taskDone();
	}
}