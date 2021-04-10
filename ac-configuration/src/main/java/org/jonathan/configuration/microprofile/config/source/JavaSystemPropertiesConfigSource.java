package org.jonathan.configuration.microprofile.config.source;

import java.util.Map;

/**
 * @description: Java 系统属性：通过 System.getProperties() 获取
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-30 23:55
 **/
public class JavaSystemPropertiesConfigSource extends MapBasedConfigSource {
    /*
    Java 系统属性最好通过本地变量保存，使用Map保存，尽可能运行期不去调整
    -Dapplicationn.name=user-web
     */
    public JavaSystemPropertiesConfigSource() {
        super("Java System Properties Config Source", 200);
    }

    @Override
    protected void prepareConfigData(Map configData) {
        logger.info("Load config from java system properties success");
        configData.putAll(System.getProperties());
    }
}
