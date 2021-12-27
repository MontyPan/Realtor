package us.dontcareabout.realtor.client.data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.realtor.client.data.RealEstateReadyEvent.RealEstateReadyHandler;

public class RealEstateReadyEvent extends GwtEvent<RealEstateReadyHandler> {
	public static final Type<RealEstateReadyHandler> TYPE = new Type<RealEstateReadyHandler>();

	public final String name;

	public RealEstateReadyEvent(String name) {
		this.name = name;
	}

	@Override
	public Type<RealEstateReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RealEstateReadyHandler handler) {
		handler.onRealEstateReady(this);
	}

	public interface RealEstateReadyHandler extends EventHandler{
		public void onRealEstateReady(RealEstateReadyEvent event);
	}
}
