package sk.stuba.fei.uim.oop.assignment3.cart.web.bodies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartProduct;


@Getter
@NoArgsConstructor
public class CartProductRequest {
    private Long productId;
    private int amount;

    public CartProductRequest(CartProduct cartProduct) {
        this.productId = cartProduct.getProduct().getId();
        this.amount = cartProduct.getAmount();
    }
}
