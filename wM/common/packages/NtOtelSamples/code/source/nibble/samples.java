package nibble;

// -----( IS Java Code Template v1.2

import com.nibble.otel.attr.AttributesUtil;
import com.nibble.otel.config.ServiceKeys;
import com.softwareag.pg.rest.RestMessageContext;
import com.wm.app.b2b.server.ServiceException;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import io.opentelemetry.api.trace.Span;
// --- <<IS-END-IMPORTS>> ---

public final class samples {
	// ---( internal utility methods )---

	final static samples _instance = new samples();

	static samples _newInstance() {
		return new samples();
	}

	static samples _cast(Object o) {
		return (samples) o;
	}

	// ---( server methods )---


	public static final void createAttributes(IData pipeline)
			throws ServiceException {
		// --- <<IS-START(createAttributes)>> ---
		// @specification nibble.otelscope.docTypes:attributesProviderSpec
		// @subtype unknown
		// @sigtype java 3.5
		IDataCursor cursor = pipeline.getCursor();
		IData id = IDataFactory.create(new Object[][]{{"mynode-svc", "xps-rsingh-svc"}});
		Span span = Span.current();
		cursor.insertAfter(ServiceKeys.providerOutput, IDataFactory.create(new Object[][]{{AttributesUtil.getPipelineDocumentName(), id}}));
		cursor.destroy();
		// --- <<IS-END>> ---
	}

	public static final void createAttributesRestApi(IData pipeline)
			throws ServiceException {
		// --- <<IS-START(createAttributesRestApi)>> ---
		// @specification nibble.otelscope.docTypes:apiAttributesProviderSpec
		// @subtype unknown
		// @sigtype java 3.5
		IDataCursor cursor = pipeline.getCursor();
		IData pipelineInput = IDataUtil.getIData(cursor, ServiceKeys.providerInput);
		IDataCursor pipelineInputCursor = pipelineInput.getCursor();
		IData messageContext = IDataUtil.getIData(pipelineInputCursor, ServiceKeys.messageContext);
		String type = IDataUtil.getString(pipelineInputCursor, ServiceKeys.type);
		pipelineInputCursor.destroy();
		IDataCursor messageContextCursor = messageContext.getCursor();
		RestMessageContext restMessageContext = (RestMessageContext) IDataUtil.get(messageContextCursor, ServiceKeys.messageContext);
		messageContextCursor.destroy();
		if ("start".equals(type)) {
			IData attr = IDataFactory.create();
			IDataCursor attrCursor = attr.getCursor();
			for (Object key : restMessageContext.getPropertyKeySet() ) {
				IDataUtil.put(attrCursor, key.toString(), restMessageContext.getProperty(key.toString()));
			}
			attrCursor.destroy();;
			cursor.insertAfter(ServiceKeys.providerOutput, IDataFactory.create(new Object[][]{{AttributesUtil.getPipelineDocumentName(), attr}}));
		} else {
			cursor.insertAfter(ServiceKeys.providerOutput, IDataFactory.create(new Object[][]{{AttributesUtil.getPipelineDocumentName(), IDataFactory.create(new Object[][]{{"nativeReturnCode", restMessageContext.getHttpReturnCode()}})}}));
		}
		cursor.destroy();
		// --- <<IS-END>> ---
	}

	public static final void createSpanName(IData pipeline)
			throws ServiceException {
		// --- <<IS-START(createSpanName)>> ---
		// @specification nibble.otelscope.docTypes:spanNameProviderSpec
		// @subtype unknown
		// @sigtype java 3.5
		IDataCursor cursor = pipeline.getCursor();
		IData pipelineInput = IDataUtil.getIData(cursor, ServiceKeys.providerInput);
		IDataCursor pipelineInputCursor = pipelineInput.getCursor();
		String name = IDataUtil.getString(pipelineInputCursor, ServiceKeys.name);
		String pkg = IDataUtil.getString(pipelineInputCursor, ServiceKeys.pkg);
		pipelineInputCursor.destroy();
		cursor.insertAfter(ServiceKeys.providerOutput, IDataFactory.create(new Object[][]{{ServiceKeys.spanName, name + "[" + pkg + "]"}}));
		cursor.destroy();
		// --- <<IS-END>> ---
	}

	public static final void createSpanNameRestApi(IData pipeline)
			throws ServiceException {
		// --- <<IS-START(createSpanNameRestApi)>> ---
		// @specification nibble.otelscope.docTypes:apiSpanNameProviderSpec
		// @subtype unknown
		// @sigtype java 3.5
		IDataCursor cursor = pipeline.getCursor();
		IData pipelineInput = IDataUtil.getIData(cursor, ServiceKeys.providerInput);
		IDataCursor pipelineInputCursor = pipelineInput.getCursor();
		IData apiService = IDataUtil.getIData(pipelineInputCursor, ServiceKeys.apiService);
		pipelineInputCursor.destroy();
		IDataCursor apiServiceCursor = apiService.getCursor();
		String id = IDataUtil.getString(apiServiceCursor, ServiceKeys.id);
		String name = IDataUtil.getString(apiServiceCursor, ServiceKeys.name);
		String version = IDataUtil.getString(apiServiceCursor, ServiceKeys.version);
		apiServiceCursor.destroy();
		cursor.insertAfter(ServiceKeys.providerOutput, IDataFactory.create(new Object[][]{{ServiceKeys.spanName, name + ":" + version + "[" + id +"]"}}));
		cursor.destroy();
		// --- <<IS-END>> ---
	}
}
