package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.Cart;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartProduct;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ICartProductRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ICartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartProductRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;


@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository repository;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICartProductRepository cartProductRepository;


    @Override
    public Cart create() {
        return this.repository.save(new Cart());
    }

    @Override
    public Cart getCartById(long id) throws NotFoundException {
        Cart cart = this.repository.findCartById(id);
        if (cart == null) {
            throw new NotFoundException();
        }
        return cart;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        Cart cart = this.getCartById(id);
        if (cart == null) {
            throw new NotFoundException();
        }
        this.repository.delete(cart);
    }

    @Override
    public Cart addCartToList(long id, CartProductRequest request) throws NotFoundException, IllegalOperationException {
        Cart cart = this.getCartById(id);
        Product product = this.productService.getProductById(request.getProductId());
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        if (product.getAmount() < request.getAmount()) {
            throw new IllegalOperationException();
        }
        CartProduct cartProduct = new CartProduct(this.productService.getProductById(request.getProductId()), request.getAmount());
        for (CartProduct c : cart.getShoppingList()) {
            if (this.productService.getProductById(c.getProduct().getId()) == this.productService.getProductById(request.getProductId())) {
                c.setAmount(c.getAmount() + request.getAmount());
                this.cartProductRepository.save(c);
                product.setAmount((product.getAmount() - request.getAmount()));
                return this.repository.save(cart);
            }
        }
        cart.getShoppingList().add(cartProduct);
        product.setAmount((product.getAmount() - request.getAmount()));
        this.cartProductRepository.save(cartProduct);
        return this.repository.save(cart);
    }

    @Override
    public double payCart(long id) throws NotFoundException, IllegalOperationException {
        Cart cart = this.getCartById(id);
        double price = 0;
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        for (CartProduct c : cart.getShoppingList()) {
            price += c.getAmount() * this.productService.getProductById(c.getProduct().getId()).getPrice();
        }
        cart.setPayed(true);
        this.repository.save(cart);
        return price;
    }

}
