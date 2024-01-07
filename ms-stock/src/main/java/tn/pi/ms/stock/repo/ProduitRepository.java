package tn.pi.ms.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.pi.ms.stock.entity.Produit;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByRef(String ref);
}
