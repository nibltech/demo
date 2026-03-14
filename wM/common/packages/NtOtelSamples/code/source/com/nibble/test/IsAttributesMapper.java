package com.nibble.test;

import com.nibble.otel.attr.AttributesUtil;
import com.nibble.otel.attr.CommonAttributesProvider;
import com.nibble.otel.event.ThreadEvent.Type;
import com.wm.app.b2b.server.invoke.ServiceStatus;
import com.wm.data.IData;
import com.wm.lang.ns.NSService;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.Span;

public class IsAttributesMapper implements CommonAttributesProvider {

	@Override
	public AttributesBuilder getAttributesBuilder() {
		AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
		return builder.put("mynode", "xps-rsingh");
	}

	@Override
	public AttributesBuilder getAttributesBuilder(NSService service, ServiceStatus status, IData pipeline, Throwable exception, Type type) {
		AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
		Span span = Span.current();
		return builder.put("mynode2", "xps-rsingh2");
	}
}
