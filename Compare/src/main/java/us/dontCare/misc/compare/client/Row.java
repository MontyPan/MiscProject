package us.dontCare.misc.compare.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public 	class Row {
	final int id;
	final String string;
	
	public Row(int id, String string) {
		this.id = id;
		this.string = string;
	}

	public int getId() {
		return id;
	}

	public String getString() {
		return string;
	}

	@Override
	public String toString() {
		return string;
	}
	
	public static final RowProperty properties = GWT.create(RowProperty.class);
	interface RowProperty extends PropertyAccess<Row> {
		ModelKeyProvider<Row> id();
		@Path("id")
		ValueProvider<Row, Integer> number();
		ValueProvider<Row, String> string();
	}
}