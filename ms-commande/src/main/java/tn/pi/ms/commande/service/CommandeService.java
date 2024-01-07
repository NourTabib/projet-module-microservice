package tn.pi.ms.commande.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tn.pi.ms.commande.entity.Commande;
import tn.pi.ms.commande.entity.CommandeEvent;
import tn.pi.ms.commande.entity.Produit;
import tn.pi.ms.commande.eventhandler.CommandeEventListener;
import tn.pi.ms.commande.repo.CommandeRepository;
import tn.pi.ms.commande.repo.ItemRepository;
import tn.pi.ms.commande.repo.ProduitRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService implements tn.pi.ms.commande.service.Service<Commande> {

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProduitRepository produitRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(
            CommandeEventListener.class
    );
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;


    @Autowired
    CommandeService(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> findById(Long id) {
        return commandeRepository.findById(id);
    }

    @Override
    public Commande save(Commande entity) {
        entity.setStatus("WAITING");
        Commande commande = commandeRepository.save(entity);
        CommandeEvent commandeEvent = new CommandeEvent();
        commandeEvent.setEventType("CREATION");
        commandeEvent.setCreationDate(new Date());
        commandeEvent.setData(commande);
        boolean sent = publishEvent(commandeEvent,"commandes");
        if(sent){
            return commande;
        }
        else {
            return null;
        }
    }

    @Override
    public Commande delete(Long id) {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()){
            commandeRepository.delete(commande.get());
            return commande.get();
        }
        return null;
    }
    public boolean publishEvent(CommandeEvent event, String topicName){
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
