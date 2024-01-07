package tn.pi.ms.commande.controller;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.pi.ms.commande.entity.Commande;
import tn.pi.ms.commande.service.CommandeService;

import java.util.Optional;

@RestController
@RequestMapping("/api/commande")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    @GetMapping
    public Commande findById(@PathVariable Long id){
        try{
            Optional<Commande> commande = commandeService.findById(id);
            if(commande.isPresent()){
                return commande.get();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping
    public Commande save(@RequestBody Commande commande){

        return commandeService.save(commande);
    }

}
