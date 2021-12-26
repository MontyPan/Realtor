package us.dontcareabout.realtor.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;

public interface HtmlTemplate extends XTemplates {
	static HtmlTemplate tplt = GWT.create(HtmlTemplate.class);

	@XTemplate(source = "REGridOffline.html")
	SafeHtml reGridOffline(Boolean offline);
}
