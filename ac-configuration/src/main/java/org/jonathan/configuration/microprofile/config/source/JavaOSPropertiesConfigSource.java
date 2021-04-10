package org.jonathan.configuration.microprofile.config.source;

import java.util.Map;

/**
 * OS 环境变量：通过 System.getenv() 获取
 */
public class JavaOSPropertiesConfigSource extends MapBasedConfigSource {

    public JavaOSPropertiesConfigSource() {
        super("Java OS Properties Config Source", 100);
    }

    @Override
    protected void prepareConfigData(Map configData) {
        logger.info("Load config from OS properties success");
        configData.putAll(System.getProperties());
    }
}
