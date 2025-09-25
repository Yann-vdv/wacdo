package com.gdu.wacdo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class WacdoApplication {

	public static void main(String[] args) {

        String trustStorePath = Objects.requireNonNull(WacdoApplication.class.getResource("/ca-truststore.jks")).getPath();
        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", "W@cdoP@ss");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

		SpringApplication.run(WacdoApplication.class, args);
	}

}
