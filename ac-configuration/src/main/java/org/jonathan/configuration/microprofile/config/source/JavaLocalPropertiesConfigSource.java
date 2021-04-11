package org.jonathan.configuration.microprofile.config.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 *本地配置文件：读取配置文件 META-INF/config.properties
 */
public class JavaLocalPropertiesConfigSource extends MapBasedConfigSource {

    private static final String MICROPROFILE_CONFIG_FILE_URL = "META-INF/config.properties";

    public JavaLocalPropertiesConfigSource() {
        super("Java Local Properties Config Source", 300);
    }


    @Override
    protected void prepareConfigData(Map configData) {
        try {
            Properties properties = new Properties();
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(MICROPROFILE_CONFIG_FILE_URL);
            if (in == null) {
                logger.info("The default config file[META-INF/config.properties] can not be found");
                return;
            }

            properties.load(in);
            configData.putAll(properties);
            logger.info("Load config from default config file[META-INF/config.properties] success");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
