package us.dontcareabout.realtor.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.HandlerRegistration;

import us.dontcareabout.realtor.client.data.DataCenter;
import us.dontcareabout.realtor.client.util.gf.Task;

public class FetchRealEstateTask extends Task {
	final String name;
	final HandlerRegistration hr;

	public FetchRealEstateTask(String name) {
		super("讀取：" + name);
		this.name = name;
		this.hr = DataCenter.addRealEstateReady(event -> {
			if (event.name.equals(name)) { finish(); }
		});
	}

	@Override
	protected void start() {
		DataCenter.wantRealEstate(name);
	}

	private void finish() {
		hr.removeHandler();
		Scheduler.get().scheduleFixedDelay(
			() -> {
				complete();
				return false;
			}, 100
		);
	}
}