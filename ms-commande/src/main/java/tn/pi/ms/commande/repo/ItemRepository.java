package tn.pi.ms.commande.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.pi.ms.commande.entity.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {
}
