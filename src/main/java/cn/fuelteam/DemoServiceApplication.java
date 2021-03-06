package cn.fuelteam;

import java.io.IOException;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@EnableAspectJAutoProxy
@DubboComponentScan(value = "cn.fuelteam")
@EnableTransactionManagement(proxyTargetClass = true, order = Ordered.HIGHEST_PRECEDENCE)
@SpringBootApplication(scanBasePackages = { "cn.fuelteam.user", "cn.fuelteam.data" })
public class DemoServiceApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(DemoServiceApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
