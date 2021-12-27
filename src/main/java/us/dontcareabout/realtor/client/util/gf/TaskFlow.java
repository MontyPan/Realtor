package us.dontcareabout.realtor.client.util.gf;

import java.util.ArrayList;
import java.util.Date;

import us.dontcareabout.gwt.client.data.Callback;

//XXX 簡單應急版本，至少還需要思考 Task 出現 error 的狀況
public class TaskFlow {
	private ArrayList<Task> list = new ArrayList<>();
	private Callback<Long> finishCB;
	private long startTime;
	private int index;

	public TaskFlow add(Task task) {
		task.setFlow(this);
		list.add(task);
		return this;
	}

	public TaskFlow finish(Callback<Long> callback) {
		this.finishCB = callback;
		return this;
	}

	public void start() {
		if (startTime == 0) { startTime = new Date().getTime(); }

		if (index == list.size()) {
			finishCB.onSuccess(new Date().getTime() - startTime);
			return;
		}

		list.get(index).start();
	}

	public double getProgress() {
		return 1.0 * index / list.size();
	}

	void taskDone() {
		index++;
		start();
	}
}