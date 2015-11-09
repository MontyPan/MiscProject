package us.dontCare.misc.compare.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

public class CompareEP implements EntryPoint {
	@Override
	public void onModuleLoad() {
		Viewport vp = new Viewport();
		vp.add(new View());
		RootPanel.get().add(vp);
	}
}
