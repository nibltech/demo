package com.nibble.test;

import com.nibble.otel.attr.AttributesUtil;
import com.nibble.otel.attr.CommonAttributesProvider;
import com.nibble.otel.attr.DefaultAttributesProviderFactory;
import io.opentelemetry.api.common.AttributesBuilder;

public class TestAttributesBuilderFactory extends DefaultAttributesProviderFactory {
    @Override
    protected CommonAttributesProvider createAttributesProvider() {
        return (service, status, pipeline, exception, type) -> {
            AttributesBuilder builder = AttributesUtil.getDefaultBuilder();
            return builder.put("mynode3", "xps-rsingh");
        };
    }
}