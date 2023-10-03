package nibble.demo.admin;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class gateway

{
	// ---( internal utility methods )---

	final static gateway _instance = new gateway();

	static gateway _newInstance() { return new gateway(); }

	static gateway _cast(Object o) { return (gateway)o; }

	// ---( server methods )---




	public static final void registerApiThreaded (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(registerApiThreaded)>> ---
		// @sigtype java 3.5
		Service.doThreadInvoke("nibble.demo.admin.gateway", "registerApi", pipeline);
		// --- <<IS-END>> ---

                
	}
}

