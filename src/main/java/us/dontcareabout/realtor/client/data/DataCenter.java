package us.dontcareabout.realtor.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.gst.client.data.ApiKey;
import us.dontcareabout.gst.client.data.SheetIdDao;
import us.dontcareabout.gwt.client.google.sheet.Sheet;
import us.dontcareabout.gwt.client.google.sheet.SheetDto;
import us.dontcareabout.realtor.client.data.RealEstateReadyEvent.RealEstateReadyHandler;
import us.dontcareabout.realtor.client.vo.RealEstate;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();

	private static HashMap<String, Sheet<RealEstate>> reMap = new HashMap<>();

	public static List<RealEstate> getAllRE() {
		ArrayList<RealEstate> result = new ArrayList<>();

		for (String name : reMap.keySet()) {
			result.addAll(reMap.get(name).getRows());
		}

		return result;
	}

	public static void wantRealEstate(String name) {
		new SheetDto<RealEstate>()
			.key(ApiKey.priorityValue()).sheetId(SheetIdDao.priorityValue())
			.validator(RealEstate.validator)
			.tabName(name)
			.fetch(result -> {
				reMap.put(name, result);
				eventBus.fireEvent(new RealEstateReadyEvent(name));
			});
	}

	public static HandlerRegistration addRealEstateReady(RealEstateReadyHandler handler) {
		return eventBus.addHandler(RealEstateReadyEvent.TYPE, handler);
	}
}
