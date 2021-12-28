package us.dontcareabout.realtor.client.util.gf;

import java.util.ArrayList;
import java.util.Date;

//XXX 簡單應急版本，至少還需要思考 Task 出現 error 的狀況
public class TaskFlow {
	private ArrayList<Task> list = new ArrayList<>();
	private Runnable startCB;
	private Runnable endCB;
	private Runnable taskStartCB;
	private Runnable taskEndCB;
	private Date startTime;
	private Date endTime;
	private int index;

	public TaskFlow add(Task task) {
		task.setFlow(this);
		list.add(task);
		return this;
	}

	public TaskFlow onStart(Runnable callback) {
		startCB = callback;
		return this;
	}

	public TaskFlow onEnd(Runnable callback) {
		this.endCB = callback;
		return this;
	}

	public TaskFlow onTaskStart(Runnable callback) {
		this.taskStartCB = callback;
		return this;
	}

	public TaskFlow onTaskEnd(Runnable callback) {
		this.taskEndCB = callback;
		return this;
	}

	public void start() {
		if (startTime == null) {
			startTime = new Date();
			runCB(startCB);
		}

		if (index == list.size()) {
			endTime = new Date();
			runCB(endCB);
			return;
		}

		runCB(taskStartCB);
		list.get(index).start();
	}

	public double getProgress() {
		return 1.0 * index / list.size();
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Task getCurrentTask() {
		if (list.isEmpty()) { return null; }
		if (index == list.size()) { return null; }
		return list.get(index);
	}

	void taskDone() {
		index++;

		runCB(taskEndCB);
		start();
	}

	private static void runCB(Runnable callback) {
		if (callback == null) { return; }
		callback.run();;
	}
}