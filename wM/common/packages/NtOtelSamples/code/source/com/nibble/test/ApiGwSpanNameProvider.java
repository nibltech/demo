package com.nibble.test;

import com.nibble.otel.span.ApiSpanNameProvider;
import com.nibble.otel.span.SpanName;
import com.softwareag.pg.service.Service;
import com.wm.app.b2b.server.invoke.ServiceStatus;
import com.wm.data.IData;
import com.wm.lang.ns.NSService;
import org.apache.synapse.MessageContext;

public class ApiGwSpanNameProvider implements ApiSpanNameProvider {
    @Override
    public SpanName getSpanName(Service service, MessageContext context) {
        return () -> service.getApiName() + "[" + service.getApiId() + "]" + " REST";
    }

    @Override
    public SpanName getSpanName(Service service, org.apache.axis2.context.MessageContext context) {
        return () -> service.getApiName() + "[" + service.getApiId() + "]" + " SOAP";
    }

    @Override
    public SpanName getSpanName(NSService service, ServiceStatus status, IData pipeline) {
        return null;
    }
}
