package nibble.demo;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.Collections;
// --- <<IS-END-IMPORTS>> ---

public final class db

{
	// ---( internal utility methods )---

	final static db _instance = new db();

	static db _newInstance() { return new db(); }

	static db _cast(Object o) { return (db)o; }

	// ---( server methods )---




	public static final void getInvoicedOrderIds (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getInvoicedOrderIds)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:1:required invoicedOrderIds
		IDataCursor cursor = pipeline.getCursor();
		IDataUtil.put(cursor, "invoicedOrderIds", invoicedOrderIds.toArray(new String[invoicedOrderIds.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void getOrderIds (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getOrderIds)>> ---
		// @sigtype java 3.5
		// [o] field:1:required orderIds
		IDataCursor cursor = pipeline.getCursor();
		IDataUtil.put(cursor, "orderIds", orderIds.toArray(new String[orderIds.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void getOrders (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getOrders)>> ---
		// @sigtype java 3.5
		// [o] recref:1:required orders nibble.demo.docs:Order
		IDataCursor cursor = pipeline.getCursor();
		IDataUtil.put(cursor, "orders", orders);
		// --- <<IS-END>> ---

                
	}



	public static final void getShippedOrderIds (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getShippedOrderIds)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:1:required shippedOrderIds
		IDataCursor cursor = pipeline.getCursor();
		IDataUtil.put(cursor, "shippedOrderIds", shippedOrderIds.toArray(new String[shippedOrderIds.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void insertOrder (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(insertOrder)>> ---
		// @sigtype java 3.5
		// [i] recref:0:required order nibble.demo.docs:Order
		IDataCursor cursor = pipeline.getCursor();
		IData order = IDataUtil.getIData(cursor, "order");
		cursor.destroy();
		cursor = order.getCursor();
		IData header = IDataUtil.getIData(cursor, "header");
		cursor.destroy();
		cursor = header.getCursor();
		cursor.destroy();
		String orderId = IDataUtil.getString(cursor, "orderId");
		cursor.destroy();
		orderIds.add(orderId);
		cursor.destroy();
		
		// --- <<IS-END>> ---

                
	}



	public static final void invoiceOrder (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(invoiceOrder)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required orderId
		// [o] field:0:required invoiceId
		IDataCursor cursor = pipeline.getCursor();
		String orderId = IDataUtil.getString(cursor, "orderId");
		invoicedOrderIds.add(orderId);
		IDataUtil.put(cursor, "invoiceId", Long.toString(invoiceId.getAndIncrement()));
		cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void setOrders (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setOrders)>> ---
		// @sigtype java 3.5
		// [i] recref:1:required orders nibble.demo.docs:Order
		IDataCursor cursor = pipeline.getCursor();
		orders = IDataUtil.getIDataArray(cursor, "orders");
		cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void shipOrder (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(shipOrder)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required orderId
		// [o] field:0:required shipmentId
		IDataCursor cursor = pipeline.getCursor();
		String orderId = IDataUtil.getString(cursor, "orderId");
		shippedOrderIds.add(orderId);
		IDataUtil.put(cursor, "shipmentId", Long.toString(shipmentId.getAndIncrement()));
		cursor.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static IData[] orders;
	private static List<String> orderIds = (List<String>) Collections.synchronizedList(new ArrayList<String>());
	private static List<String> shippedOrderIds = (List<String>) Collections.synchronizedList(new ArrayList<String>());
	private static List<String> invoicedOrderIds = (List<String>) Collections.synchronizedList(new ArrayList<String>());
	private static final AtomicLong shipmentId = new AtomicLong(1);
	private static final AtomicLong invoiceId = new AtomicLong(1);
	// --- <<IS-END-SHARED>> ---
}

