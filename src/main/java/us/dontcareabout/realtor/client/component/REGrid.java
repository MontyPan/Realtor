package us.dontcareabout.realtor.client.component;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;

import us.dontcareabout.gxt.client.util.ColumnConfigBuilder;
import us.dontcareabout.realtor.client.component.REGrid.Entity;
import us.dontcareabout.realtor.client.component.gf.TreeGrid2;
import us.dontcareabout.realtor.client.util.HtmlTemplate;
import us.dontcareabout.realtor.client.vo.IRealEstate;
import us.dontcareabout.realtor.client.vo.RealEstate;

public class REGrid extends TreeGrid2<Entity> {
	private static Properties properties = GWT.create(Properties.class);
	private static NumberCell<Double> numberCell = new NumberCell<>(NumberFormat.getFormat("#,##0.##"));

	public REGrid() {
		grid.getTreeView().setForceFit(true);
	}

	public void refresh(List<RealEstate> data) {
		TreeStore<Entity> store = grid.getTreeStore();
		HashSet<Entity> sectionSet = new HashSet<>();

		for (RealEstate re : data) {
			Entity areaNode = ensureNode(re.getArea());
			Entity sectionNode = ensureNode(areaNode, re.getSection());
			Entity blockNode = ensureNode(sectionNode, re.getBlock());
			store.add(blockNode, new Entity(re));
			sectionSet.add(sectionNode);
		}

		//展開到可看見「小段」
		for (Entity section : sectionSet) {
			grid.setExpanded(section, true);
		}
	}

	@Override
	protected ColumnConfig<Entity, ?> genTreeColumn() {
		return new ColumnConfigBuilder<Entity, String>(properties.name())
			.setHorizontalHeaderAlignment(ALIGN_CENTER)
			.setHeader("地籍資訊").setWidth(150).build();
	}

	@Override
	protected TreeStore<Entity> genTreeStore() {
		return new TreeStore<>(item -> item.getId());
	}

	@Override
	protected ColumnModel<Entity> genColumnModel() {
		List<ColumnConfig<Entity, ?>> list = new ArrayList<>();
		list.add(treeColumn);
		list.add(
			new ColumnConfigBuilder<Entity, Boolean>(properties.offline())
				.setHeader("下架").setHorizontalHeaderAlignment(ALIGN_CENTER)
				.setWidth(50).setFixed(true).setCellPedding(false)
				.setCell(new AbstractCell<Boolean>() {
					@Override
					public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
						sb.append(HtmlTemplate.tplt.reGridOffline(value));
					}
				}).build()
		);
		list.add(
			genDoubleConfigBuilder(properties.squarePing())
				.setHeader("坪數").build()
		);
		list.add(
			genDoubleConfigBuilder(properties.rightPing())
				.setHeader("權利坪數").build()
		);
		list.add(
			genDoubleConfigBuilder(properties.announcePrice())
				.setHeader("公告現值").build()
		);
		list.add(
			genDoubleConfigBuilder(properties.sellPrice())
				.setHeader("售價").build()
		);
		list.add(
			genDoubleConfigBuilder(properties.totalPrice())
				.setHeader("總價").build()
		);
		return new ColumnModel<>(list);
	}

	private Entity ensureNode(String name) {
		TreeStore<Entity> store = grid.getTreeStore();
		Entity result = store.findModelWithKey(name);

		if (result != null) { return result; }

		result = new Entity(name);
		store.add(result);

		return result;
	}

	private Entity ensureNode(Entity parent, String name) {
		TreeStore<Entity> store = grid.getTreeStore();
		Entity result = find(store.getAllChildren(parent), name);

		if (result != null) { return result; }

		result = new Entity(parent, name);
		store.add(parent, result);

		return result;
	}

	private static Entity find(List<Entity> list, String name) {
		for (Entity entity : list) {
			if (name.equals(entity.getName())) { return entity; }
		}

		return null;
	}

	private static ColumnConfigBuilder<Entity, Double> genDoubleConfigBuilder(ValueProvider<Entity, Double> v) {
		return new ColumnConfigBuilder<Entity, Double>(v)
			.setCell(numberCell)
			.setHorizontalHeaderAlignment(ALIGN_CENTER)
			.setHorizontalAlignment(ALIGN_RIGHT);
	}

	interface Properties extends PropertyAccess<Entity> {
		ValueProvider<Entity, String> name();

		@Path("re.offline") ValueProvider<Entity, Boolean> offline();
		@Path("re.squarePing") ValueProvider<Entity, Double> squarePing();
		@Path("re.announcePrice") ValueProvider<Entity, Double> announcePrice();
		@Path("re.sellPrice") ValueProvider<Entity, Double> sellPrice();
		@Path("re.rightPing") ValueProvider<Entity, Double> rightPing();
		@Path("re.totalPrice") ValueProvider<Entity, Double> totalPrice();
	}

	class Entity {
		final IRealEstate re;
		final String nameForTree;
		final String id;

		Entity(String name) {
			this(name, name, new DummyRealEstate());
		}

		Entity(Entity parent, String name) {
			this(name, parent.nameForTree + name, new DummyRealEstate());
		}

		Entity(RealEstate re) {
			this(re.getSerial(), re.getId(), re);
		}

		Entity(String name, String id, IRealEstate re) {
			this.nameForTree = name;
			this.id = id;
			this.re = re;
		}

		public String getName() {
			return nameForTree;
		}

		public String getId() {
			return id;
		}

		public IRealEstate getRe() {
			return re;
		}
	}

	class DummyRealEstate implements IRealEstate {
		@Override
		public String getId() { return null; }

		@Override
		public Boolean isOffline() { return false; }

		@Override
		public String getArea() { return null; }

		@Override
		public String getSection() { return null; }

		@Override
		public String getBlock() { return null; }

		@Override
		public String getSerial() { return null; }

		@Override
		public Double getSquareM2() { return null; }

		@Override
		public Double getSquarePing() { return null; }

		@Override
		public Integer getRightNumerator() { return null; }

		@Override
		public Integer getRightDenominator() { return null; }

		@Override
		public Double getRightPing() { return null; }

		@Override
		public String getUsage() { return null; }

		@Override
		public String getUsageType() { return null; }

		@Override
		public Double getAnnouncePrice() { return null; }

		@Override
		public String getReplan() { return null; }

		@Override
		public String getByRoad() { return null; }

		@Override
		public Double getSellPrice() { return null; }

		@Override
		public Double getTotalPrice() { return null; }

		@Override
		public String getRealtor() { return null; }

		@Override
		public String getNote() { return null; }
	}
}
