package tn.pi.ms.stock.eventhandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tn.pi.ms.stock.entity.Commande;
import tn.pi.ms.stock.entity.CommandeEvent;
import tn.pi.ms.stock.entity.Item;
import tn.pi.ms.stock.entity.Produit;
import tn.pi.ms.stock.repo.ProduitRepository;

import java.util.Map;
import java.util.Optional;

@Component
@EnableKafka
public class StockEventListener {


    private static final Logger LOGGER = LoggerFactory.getLogger(
            StockEventListener.class
    );
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    private final ProduitRepository produitRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public StockEventListener(KafkaTemplate<String,String> kafkaTemplate,ProduitRepository produitRepository){
        this.produitRepository = produitRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "commandes",groupId = "ms-stock")
    void listener(String message){
        try{
            Boolean status = true;
            Map<String,String> commandeEvent = objectMapper.readValue(message,Map.class);
            if(commandeEvent.get("eventType").equals("CREATE")){
                CommandeEvent commandeEvent1 = objectMapper.readValue(message,CommandeEvent.class);
                for(Item item:commandeEvent1.getData().getItems()) {
                    Optional<Produit> optionalProduit = produitRepository.findById(item.getProduit().getId());
                    if(optionalProduit.isPresent()){
                        Produit produitEnStock= optionalProduit.get();
                        if(produitEnStock.getQuantity() >= item.getQuantity()){
                            // REDUIRE ET CONFIRMER
                            produitEnStock.setQuantity(
                                    produitEnStock.getQuantity() - item.getQuantity()
                            );
                            produitRepository.save(produitEnStock);
                            // RESEND EVENT
                            commandeEvent1.getData().setStatus("CONFIRMED");
                            this.publishEvent(commandeEvent1,"commandes");
                        }else{
                            commandeEvent1.getData().setStatus("HORS_STOCK");
                            this.publishEvent(commandeEvent1,"commandes");
                        }
                    }else {
                        commandeEvent1.getData().setStatus("INDISPONIBLE");
                        this.publishEvent(commandeEvent1,"commandes");
                    }
                }

            }else{
                System.out.println("Unknown Event Type");
            }
        }catch(Exception e){
            LOGGER.error("UserListener Exception",e);
        }
    }
    public boolean publishEvent(CommandeEvent event,String topicName){
        try{
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topicName,""+payload);
            return true;
        }catch(Exception e){
            LOGGER.error("error:",e);
            return false;
        }

    }
}
