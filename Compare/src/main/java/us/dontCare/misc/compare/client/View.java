package us.dontCare.misc.compare.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.TextArea;

public class View extends Composite {
	private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
	interface ViewUiBinder extends UiBinder<Widget, View> {}
	private static final HorizontalLayoutData HALF_HLD = new HorizontalLayoutData(0.5, 1);
	
	@UiField HorizontalLayoutContainer workPanel;
	@UiField HorizontalLayoutContainer toolbar;
	@UiField TextButton button;
	@UiField IntegerField sourceRow;
	@UiField IntegerField targetRow;
	@UiField TextArea diffView;
	
	private boolean isInit = true;
	private boolean isDiff = true;
	private ArrayList<Row[]> diff = new ArrayList<>();
	
	private TextArea sourceInput = new TextArea();
	private TextArea targetInput = new TextArea();
	private ArrayList<Row> source;
	private ArrayList<Row> target;
	
	public View() {
		initWidget(uiBinder.createAndBindUi(this));
		workPanel.add(sourceInput, HALF_HLD);
		workPanel.add(targetInput, HALF_HLD);
	}
	
	@UiHandler("button")
	void clickCompare(SelectEvent se) {
		if (isInit) {
			init();
			isInit = false;
		} else {
			diff();
		}
	}
	
	@UiHandler("transform") 
	void clickTransform(SelectEvent se) {
		isDiff = !isDiff;
		refreshDiffView();
	}
	
	private void init() {
		source = normalize(sourceInput);
		target = normalize(targetInput);
		
		workPanel.clear();
		workPanel.add(build(source), HALF_HLD);
		workPanel.add(build(target), HALF_HLD);
		workPanel.forceLayout();
		button.setText("Continue");
	}
	
	private void diff() {
		int sRow = sourceRow.getValue() - 1;
		int tRow = targetRow.getValue() - 1;
		
		while(true) {
			if (!source.get(sRow).toString().equals(target.get(tRow).toString())) {
				diff.add(new Row[]{source.get(sRow), target.get(tRow)});
				refreshDiffView();
//				String message =
//					"來源第 " + (sRow + 1) + " 筆：" + source.get(sRow) + "\n" +
//					"目標第 " + (tRow + 1) + " 筆：" + target.get(tRow);
//
//				Window.alert("發現不相同！\n" + message);
				
				sourceRow.setValue(sRow + 2);
				targetRow.setValue(tRow + 2);
				break;
			}
			
			sRow++;
			if (sRow == source.size()) { break; }
			tRow++;
			if (tRow == target.size()) { break; }
		}
	}
	
	private void refreshDiffView() {
		StringBuffer result = new StringBuffer();
		
		if (isDiff) {
			for (int i = diff.size() - 1; i >= 0; i--) {
				Row[] row = diff.get(i);
				result.append(
					"來源第 " + row[0].getId() + " 筆：" + row[0] + "\n" +
					"目標第 " + row[1].getId() + " 筆：" + row[1] + "\n\n"
				);
			}
		} else {
			for (Row[] row : diff) {
				result.append(row[0] + " => " + row[1] + "\n\n");
			}
		}
		
		diffView.setText(result.toString());
	}
	
	private Widget build(ArrayList<Row> source) {
		RowGrid result = new RowGrid();
		result.getStore().addAll(source);
		result.getView().setForceFit(true);
		return result;
	}

	private static final String[][] REPLACER = {
		{"," , "，"},
		{":" , "："},
		{";" , "；"},
		{"?" , "？"},
		{"-" , "－"},
		{"(" , "（"},
		{")" , "）"}
	};
	private static final String[] SPLITTER = {"，", "。", "；", "〈", "「"};
	private ArrayList<Row> normalize(TextArea foo) {
		String result = foo.getText();
		
		result = result.replace("\n", "");	//把所有的換行符號拿掉
		
		for (String[] replacer : REPLACER) {
			result = result.replace(replacer[0], replacer[1]);
		}
		
		for (String s : SPLITTER) {
			result = result.replace(s, s + "\n");
		}
		
		return trimEmptyLine(result.split("\n"));
	}
	
	private ArrayList<Row> trimEmptyLine(String[] foo) {
		ArrayList<Row> result = new ArrayList<>();
		
		int counter = 0;
		for (String str : foo) {
			if (str.trim().length() == 0) { continue; }
			result.add(new Row(counter++, str.trim()));
		}
		
		return result;
	}
}