package com.nibble.test;

import com.nibble.otel.attr.ApiAttributesProvider;
import com.nibble.otel.attr.AttributesUtil;
import com.nibble.otel.event.ThreadEvent.Type;
import com.softwareag.pg.service.Service;
import com.wm.app.b2b.server.invoke.ServiceStatus;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataUtil;
import com.wm.lang.ns.NSService;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.Span;
import org.apache.synapse.MessageContext;

public class ApiGwAttributesMapper implements ApiAttributesProvider {

    @Override
    public AttributesBuilder getAttributesBuilder() {
        AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
        return builder.put("mynode", "xps-rsingh");
    }

    @Override
    public AttributesBuilder getAttributesBuilder(NSService service, ServiceStatus status, IData pipeline, Throwable exception, Type type) {
        AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
        if (type == Type.end && service.getNSName().getFullName().equals("pub.client:http")) {
            IDataCursor cursor = pipeline.getCursor();
            IData header = IDataUtil.getIData(cursor, "header");
            if (header != null) {
                IDataCursor hc = header.getCursor();
                String st = IDataUtil.getString(hc, "status");
                String stMessage = IDataUtil.getString(hc, "statusMessage");
                hc.destroy();
                if (st != null) {
                    builder.put(AttributeKey.stringKey("status"), st);
                }
                if (stMessage != null) {
                    builder.put(AttributeKey.stringKey("statusMessage"), stMessage);
                }
            }
            cursor.destroy();
        }
        Span span = Span.current();
        return builder.put("mynode2", "xps-rsingh2");
    }

    @Override
    public AttributesBuilder getAttributesBuilder(Service service, MessageContext context, Throwable exception, Type type) {
        AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
        Span span = Span.current();
        return builder.put("mynode2", "xps-rsingh2");
    }

    @Override
    public AttributesBuilder getAttributesBuilder(Service service, org.apache.axis2.context.MessageContext context,
                                                  Throwable exception, Type type) {
        AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
        Span span = Span.current();
        return builder.put("mynode2", "xps-rsingh2");
    }
}