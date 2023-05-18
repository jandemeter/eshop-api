package sk.stuba.fei.uim.oop.assignment3.cart.data;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<CartProduct> shoppingList;

    private boolean payed;

    public Cart() {
        this.shoppingList = new ArrayList<>();
    }

}
