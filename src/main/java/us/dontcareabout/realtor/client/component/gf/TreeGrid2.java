package us.dontcareabout.realtor.client.component.gf;

import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

/**
 * 為了對付 {@link TreeGrid} 那幾乎不合理的 constructor 設計，
 * 所以用 {@link SimpleContainer} 再包一層，視覺上不會有區別。
 * 在程式中可透過 {@link #grid} 操作真正的 {@link TreeGrid}。
 * <p>
 * 注意：在 {@link #genColumnModel()} 請記得加入 {@link #treeColumn}。
 * 因為 {@link #genTreeColumn()} 會先做，
 * 所以在 {@link #genColumnModel()} 時已經有 instance 了。
 */
public abstract class TreeGrid2<M> extends SimpleContainer {
	protected final ColumnConfig<M, ?> treeColumn;
	protected final TreeGrid<M> grid;

	protected abstract ColumnConfig<M, ?> genTreeColumn();
	protected abstract TreeStore<M> genTreeStore();

	/** 注意：請記得加入 {@link #treeColumn}*/
	protected abstract ColumnModel<M> genColumnModel();

	public TreeGrid2() {
		treeColumn = genTreeColumn();
		grid = new TreeGrid<>(genTreeStore(), genColumnModel(), treeColumn);
		add(grid);
	}
}