package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import sk.stuba.fei.uim.oop.assignment3.cart.data.Cart;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartProductRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

public interface ICartService {
    Cart create();

    Cart getCartById(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    Cart addCartToList(long id, CartProductRequest info) throws NotFoundException, IllegalOperationException;

    double payCart(long id) throws NotFoundException, IllegalOperationException;
}
