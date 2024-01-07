package tn.pi.ms.commande.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "commande",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Item> items;
    private String status;
    public double calculerTolate(){
        return items.stream().mapToDouble((item)-> item.getProduit().getPrice()).sum();
    }
}
