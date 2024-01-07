package tn.pi.ms.stock.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Item {
    private Long id;
    private Produit produit;
    private Long quantity;
    private Commande commande;
}
