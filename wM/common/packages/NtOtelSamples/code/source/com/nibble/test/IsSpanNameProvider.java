package com.nibble.test;

import com.nibble.otel.span.CommonSpanNameProvider;
import com.nibble.otel.span.SpanName;
import com.wm.app.b2b.server.invoke.ServiceStatus;
import com.wm.data.IData;
import com.wm.lang.ns.NSService;

public class IsSpanNameProvider implements CommonSpanNameProvider {
    @Override
    public SpanName getSpanName(NSService service, ServiceStatus status, IData pipeline) {
        if (service.getNSName().getFullName().equals("nibble.demo.api.resources.order_.services:postOrder")) {
            return () -> "POST /demo order";
        }
        return null;
    }
}
