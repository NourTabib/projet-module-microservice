package tn.pi.ms.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import tn.pi.ms.stock.entity.Produit;
import tn.pi.ms.stock.repo.ProduitRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceStock implements Service<Produit>{
    @Autowired
    private ProduitRepository produitRepository;
    @Override
    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    @Override
    public Optional<Produit> findById(Long id) {
        return produitRepository.findById(id);
    }


    @Override
    public Optional<Produit> findByRef(String ref) {
        return produitRepository.findByRef(ref);
    }

    @Override
    public Produit save(Produit entity) {
        return produitRepository.save(entity);
    }

    @Override
    public Produit delete(Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        if(produit.isPresent()){
            produitRepository.deleteById(id);
        }else{
            return null;
        }
        return produit.get();
    }
}
