package tn.pi.ms.commande;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceCommande {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCommande.class, args);
    }
}
