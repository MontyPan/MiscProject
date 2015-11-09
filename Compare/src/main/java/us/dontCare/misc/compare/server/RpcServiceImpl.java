package us.dontCare.misc.compare.server;

import java.util.Date;

import us.dontCare.misc.compare.client.RpcService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean before(Date clientDate) throws Exception {
		//故意暫停個一段時間，以呈現 async 的感覺
		try { Thread.sleep((long)(Math.random() * 5000) + 1000); } 
		catch (InterruptedException e) {}
		
		try {
			return new Date().compareTo(clientDate) > 0;
		} catch(NullPointerException npe) {
			throw new Exception("不要給我空值阿... 混蛋...");
		}
	}
}
