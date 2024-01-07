package tn.pi.ms.commande.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.pi.ms.commande.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
