package us.dontcareabout.realtor.client;

import com.google.gwt.user.client.Window;

import us.dontcareabout.gst.client.GSTEP;

public class RealtorEP extends GSTEP {
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
	}
}
