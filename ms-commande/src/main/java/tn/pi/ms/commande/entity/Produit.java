package tn.pi.ms.commande.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Articles")
@Table(name = "Articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Produit {
    @Id
    private Long id;
    @Column(unique = true)
    private String ref;
    private String name;
    private Long quantity;
    private Double price;
}
