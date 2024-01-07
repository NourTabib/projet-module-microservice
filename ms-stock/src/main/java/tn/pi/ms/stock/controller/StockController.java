package tn.pi.ms.stock.controller;

import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.pi.ms.stock.entity.Produit;
import tn.pi.ms.stock.service.ServiceStock;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private ServiceStock serviceStock;

    @GetMapping("/produits/")
    public  List<Produit> findAll(){
        return serviceStock.findAll();
    }
    @GetMapping("/produits/{id}")
    public Produit findById(@PathVariable Long id){
        try{
            Optional<Produit> produit = serviceStock.findById(id);
            if(produit.isPresent()){
                return produit.get();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/produits/ref/{ref}")
    public Produit findByRef(@PathVariable String ref){
        try{
            Optional<Produit> produit = serviceStock.findByRef(ref);
            if(produit.isPresent()){
                return produit.get();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/produits")
    public Produit create(@RequestBody Produit produit){
        try{
            return serviceStock.save(produit);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/produits/{id}/qty/{quantity}/verifier")
    public Long verifierDisponibilite(@PathVariable Long id,@PathVariable Long quantity){
        try{
            Optional<Produit> produit = serviceStock.findById(id);
            if(produit.isPresent()){
                if(produit.get().getQuantity()>= quantity){
                    return (produit.get().getQuantity());
                }
                return 0L;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/produits/{id}/qty/{quantity}/augmenter")
    public Long augmenterStock(@PathVariable Long id,@PathVariable Long quantity){
        try{
            Optional<Produit> produitOptional = serviceStock.findById(id);
            if(produitOptional.isPresent()){
                Produit produit = produitOptional.get();
                produit.setQuantity(produit.getQuantity()+quantity);
                serviceStock.save(produit);
                return produit.getQuantity();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/produits/{id}/qty/{quantity}/reduire")
    public Long reduireStock(Long id,Long quantity){
        try{
            Optional<Produit> produitOptional = serviceStock.findById(id);
            if(produitOptional.isPresent()){
                Produit produit = produitOptional.get();
                if(produit.getQuantity() < quantity){
                    return null;
                }
                else{
                    produit.setQuantity(produit.getQuantity()-quantity);
                    serviceStock.save(produit);
                    return produit.getQuantity();
                }
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
