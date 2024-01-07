package tn.pi.ms.commande.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit")
    private Produit produit;

    private Long quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="commande", nullable=false)
    private Commande commande;

}
