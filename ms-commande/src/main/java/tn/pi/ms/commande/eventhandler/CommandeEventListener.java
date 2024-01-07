package tn.pi.ms.commande.eventhandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tn.pi.ms.commande.entity.Commande;
import tn.pi.ms.commande.entity.CommandeEvent;
import tn.pi.ms.commande.entity.Item;
import tn.pi.ms.commande.entity.Produit;
import tn.pi.ms.commande.service.CommandeService;


import java.util.Map;
import java.util.Optional;

@Component
@EnableKafka
public class CommandeEventListener {


    private static final Logger LOGGER = LoggerFactory.getLogger(
            CommandeEventListener.class
    );
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    private final CommandeService commandeService;
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public CommandeEventListener(KafkaTemplate<String,String> kafkaTemplate, CommandeService commandeService){
        this.commandeService = commandeService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "commandes",groupId = "ms-stock")
    void listener(String message){
        try{
            Boolean status = true;
            Map<String,String> commandeEvent = objectMapper.readValue(message,Map.class);
            if(commandeEvent.get("eventType").equals("CREATE")){

            }
            else if(commandeEvent.get("eventType").isEmpty() != true){
                CommandeEvent commandeEvent1 = objectMapper.readValue(message,CommandeEvent.class);
                Optional<Commande> optionalCommande=commandeService.findById(commandeEvent1.getData().getId());
                if(optionalCommande.isPresent()){
                    Commande commande = optionalCommande.get();
                    commande.setStatus(commandeEvent.get("eventType"));
                    commandeService.save(commande);
                }else{

                }
            }
            else{
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
