package us.dontCare.misc.compare.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("RPC")
public interface RpcService extends RemoteService{
	/**
	 * @return client 指定的日期是否在 service 當下的時間之前
	 */
	boolean before(Date clientDate) throws Exception;
}
