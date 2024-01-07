package tn.pi.ms.stock.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Commande {

    private Long id;
    private List<Item> items;
    private String status;
    public double calculerTolate(){
        return items.stream().mapToDouble((item)-> item.getProduit().getPrice()).sum();
    }
}
