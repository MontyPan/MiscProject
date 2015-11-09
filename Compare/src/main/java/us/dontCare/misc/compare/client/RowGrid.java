package us.dontCare.misc.compare.client;

import java.util.ArrayList;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class RowGrid extends Grid<Row>{
	public RowGrid() {
		super(
			new ListStore<Row>(Row.properties.id()),
			genColumnModel()
		);
	}
	
	private static ColumnModel<Row> genColumnModel() {
		ColumnConfig<Row, Integer> numberColumn = new ColumnConfig<>(Row.properties.number(), 32, "行數");
		numberColumn.setSortable(false);
		
		ColumnConfig<Row, String> stringColumn = new ColumnConfig<>(Row.properties.string(), 300, "字串");
		stringColumn.setSortable(false);

		ArrayList<ColumnConfig<Row, ?>> list = new ArrayList<>();
		list.add(numberColumn);
		list.add(stringColumn);
		
		return new ColumnModel<Row>(list);
	}
}
