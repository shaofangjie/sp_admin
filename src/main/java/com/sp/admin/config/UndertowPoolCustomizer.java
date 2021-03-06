package com.sp.admin.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
/**
 *  undertow警告Buffer pool was not set on WebSocketDeploymentInfo
 *  看提示让你设置一下buffer pool,不然他就使用默认的
 *  这个警告不影响使用,但是看着别扭,于是
 *  根据官方文档,和源码,自定义配置
 * @time 2020/6/13 23:54
 * @return
 */

@Component
public class UndertowPoolCustomizer implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {
    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });
    }
}
