package cn.com.yunyoutianxia.tour.init;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;
import java.util.Set;

/**
 * @Auther: zhiwutu
 * @Date: 2018/8/13 15:10
 * @Description:  apollo日志配置读取类
 */
public class ServletContextLog4jInitListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 从Apollo读取log4j配置
        Config config = ConfigService.getAppConfig();
        Set<String> propertyNames = config.getPropertyNames();
        //log4j手动加载配置文件
        Properties properties = new Properties();
        for (String propertyName : propertyNames) {
            if (propertyName.contains("log4j")) {
                String propertyValue = config.getProperty(propertyName, null);
                properties.setProperty(propertyName, propertyValue);
            }
        }
        PropertyConfigurator.configure(properties);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
