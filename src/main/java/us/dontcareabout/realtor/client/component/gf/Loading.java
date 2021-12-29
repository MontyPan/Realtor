package us.dontcareabout.realtor.client.component.gf;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;

import us.dontcareabout.gxt.client.component.RwdRootPanel;
import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;
import us.dontcareabout.gxt.client.draw.layout.VerticalLayoutLayer;

//XXX 該開放 config 嘛.....
public class Loading {
	private static View instance = new View();
	private static Window dialog = new Window();
	static {
		dialog.setModal(true);
		dialog.setResizable(false);
		dialog.setHeaderVisible(false);
		dialog.add(instance);
	}

	public static void show() {
		dialog.show();
		dialog.setPixelSize(RwdRootPanel.getWidth() * 8 / 10, 64);
		instance.setWidth(RwdRootPanel.getWidth() * 8 / 10 - 4);
		dialog.center();
	}

	public static void hide() {
		dialog.hide();
	}

	public static void upgrade(double progress) {
		instance.progress.setRatio(progress);
		instance.redrawSurface();
	}

	public static void upgrade(String message) {
		instance.message.setText(message);
		instance.redrawSurface();
	}

	static class View extends LayerContainer {
		private VerticalLayoutLayer root = new VerticalLayoutLayer();
		private Progress progress = new Progress();
		private TextButton message = new TextButton();

		private View() {
			Margins margins = new Margins(2, 0, 0, 0);
			message.setMargins(margins);
			root.setMargins(margins);
			root.setGap(2);
			root.addChild(progress, 1);
			root.addChild(message, 26);
			addLayer(root);
		}

		@Override
		protected void adjustMember(int width, int height) {
			root.resize(width, height);
		}

		private class Progress extends LayerSprite {
			LRectangleSprite bar = new LRectangleSprite();
			double ratio;

			Progress() {
				bar.setFill(new RGB(0, 120, 180));
				add(bar);
			}

			void setRatio(double ratio) {
				this.ratio = ratio;
				adjustMember();
			}

			@Override
			protected void adjustMember() {
				bar.setLX(0);
				bar.setLY(0);
				bar.setHeight(getHeight());
				bar.setWidth(getWidth() * ratio);
			}
		}
	}
}
