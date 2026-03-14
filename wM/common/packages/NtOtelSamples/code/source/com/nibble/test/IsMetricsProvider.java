package com.nibble.test;

import com.nibble.otel.event.ThreadEvent;
import com.nibble.otel.metrics.CommonMetricsProvider;
import com.wm.app.b2b.server.invoke.ServiceStatus;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataUtil;
import com.wm.lang.ns.NSService;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.MeterProvider;

public class IsMetricsProvider implements CommonMetricsProvider {

    private LongCounter orderCounter;
    private LongCounter orderQuantityCounter;
    private static final AttributesBuilder builder = Attributes.builder();
    private static final AttributeKey<String> CLIENT_ID = AttributeKey.stringKey("clientId");

    @Override
    public void registerMetrics(MeterProvider meterProvider, NSService service, ServiceStatus status, IData pipeline, Throwable exception, ThreadEvent.Type type) {
        Meter meter = meterProvider.get(IsMetricsProvider.class.getName());

        if (orderCounter == null) {
            orderCounter = meter
                    .counterBuilder("demo.samples.orders.orderCount")
                    .setDescription("Number of orders")
                    .setUnit("1")
                    .build();
            orderQuantityCounter = meter
                    .counterBuilder("demo.samples.orders.orderAmount")
                    .setUnit("1")
                    .build();
        }
        IDataCursor pipelineCursor = pipeline.getCursor();
        IData order = IDataUtil.getIData(pipelineCursor, "nibble.demo.docs:Order");
        pipelineCursor.destroy();
        IDataCursor orderCursor = order.getCursor();
        IData header = IDataUtil.getIData(orderCursor, "header");
        IDataCursor hc = header.getCursor();
        String clientId = IDataUtil.getString(hc, "clientId");
        hc.destroy();
        Attributes attributes = builder.put(CLIENT_ID, clientId).build();
        orderCounter.add(1, attributes);
        IData[] lines = IDataUtil.getIDataArray(orderCursor, "lines");
        orderCursor.destroy();
        long orderQuantitySum = 0;
        for (IData line : lines) {
            IDataCursor lineCursor = line.getCursor();
            int quantity = IDataUtil.getInt(lineCursor, "quantity", 0);
            orderQuantitySum += quantity;
            lineCursor.destroy();
        }
        orderCursor.destroy();
        orderQuantityCounter.add(orderQuantitySum, builder.put(CLIENT_ID, clientId).build());
    }
}
