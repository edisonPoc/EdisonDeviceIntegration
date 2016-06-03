package com.example.AzureIotDeviceIntegration;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages="com.example.AzureIotDeviceIntegration")
public class AzureIotDeviceIntegrationApplication extends SpringBootServletInitializer{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AzureIotDeviceIntegrationApplication.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(AzureIotDeviceIntegrationApplication.class, args);
	}
}
