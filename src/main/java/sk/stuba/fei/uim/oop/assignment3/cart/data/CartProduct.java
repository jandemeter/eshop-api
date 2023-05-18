package sk.stuba.fei.uim.oop.assignment3.cart.data;

import lombok.Data;
import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

import javax.persistence.*;

@Data
@Getter
@Entity
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    private int amount;

    public CartProduct() {
    }

    public CartProduct(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

}
