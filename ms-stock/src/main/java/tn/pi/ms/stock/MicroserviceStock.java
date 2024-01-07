package tn.pi.ms.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceStock {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceStock.class, args);

    }
}
