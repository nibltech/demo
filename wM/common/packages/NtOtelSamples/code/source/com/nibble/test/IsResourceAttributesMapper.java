package com.nibble.test;

import com.nibble.otel.attr.AttributesUtil;
import com.nibble.otel.attr.CommonAttributesProvider;
import com.nibble.otel.event.ThreadEvent.Type;
import com.wm.app.b2b.server.invoke.ServiceStatus;
import com.wm.data.IData;
import com.wm.lang.ns.NSService;
import io.opentelemetry.api.common.AttributesBuilder;

public class IsResourceAttributesMapper implements CommonAttributesProvider {

    @Override
    public AttributesBuilder getAttributesBuilder() {
        AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
        return builder.put("resource-node", "xps-rsingh");
    }

    @Override
    public AttributesBuilder getAttributesBuilder(NSService service, ServiceStatus status, IData pipeline, Throwable exception,
                                                  Type type) {
        throw new RuntimeException("Not implemented");
    }
}
