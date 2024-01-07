package tn.pi.ms.commande.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.pi.ms.commande.entity.Commande;

public interface CommandeRepository  extends JpaRepository<Commande, Long> {
}
