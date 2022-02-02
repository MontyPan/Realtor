package us.dontcareabout.realtor.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import us.dontcareabout.gst.client.GSTEP;
import us.dontcareabout.gxt.client.component.RwdRootPanel;
import us.dontcareabout.realtor.client.component.REGrid;
import us.dontcareabout.realtor.client.component.gf.Loading;
import us.dontcareabout.realtor.client.data.DataCenter;
import us.dontcareabout.realtor.client.util.gf.TaskFlow;

public class RealtorEP extends GSTEP {
	private TaskFlow flow = new TaskFlow();

	public RealtorEP() {
		super("Realtor", "1mky8dxwveDYOg6qeelfePup33xly2LGJJDyBvSc7vQo");
	}

	@Override
	protected String version() { return "0.0.1"; }

	@Override
	protected String defaultLocale() { return "zh_TW"; }

	@Override
	protected void featureFail() {
		Window.alert("這個瀏覽器我不尬意，不給用..... \\囧/");
	}

	@Override
	protected void start() {
		RootPanel.get("loading").setVisible(false);

		REGrid grid = new REGrid();
		RwdRootPanel.setComponent(grid);

		flow.onStart(Loading::show)
			.onEnd(() -> {
				Scheduler.get().scheduleFixedDelay(() -> {
					Loading.hide();
					grid.refresh(DataCenter.getAllRE());
					return false;
				}, 100);
			})
			.onTaskStart(() -> Loading.upgrade(flow.getCurrentTask().name))
			.onTaskEnd(() -> Loading.upgrade(flow.getProgress()))
		;

		String[] tabName = {
		};
		for (String name : tabName) {
			flow.add(new FetchRealEstateTask(name));
		}

		flow.start();
	}
}
