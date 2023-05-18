package sk.stuba.fei.uim.oop.assignment3.product.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.IProductRepository;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductRequest;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductUpdateRequest;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository repository;

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product create(ProductRequest request) {
        Product p = new Product();
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        p.setAmount(request.getAmount());
        p.setUnit(request.getUnit());
        p.setPrice(request.getPrice());
        return this.repository.save(p);
    }

    @Override
    public Product getProductById(long id) throws NotFoundException {
        Product p = this.repository.findProductById(id);
        if (p == null) {
            throw new NotFoundException();
        }
        return p;

    }

    @Override
    public Product update(long id, ProductUpdateRequest request) throws NotFoundException {
        Product p = this.getProductById(id);
        if (request.getName() != null) {
            p.setName(request.getName());
        }
        if (request.getDescription() != null) {
            p.setDescription(request.getDescription());
        }
        return this.repository.save(p);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(getProductById(id));
    }

    @Override
    public int getProductAmount(long id) throws NotFoundException {
        return this.getProductById(id).getAmount();
    }

    @Override
    public int addProductAmount(long id, int amount) throws NotFoundException {
        Product p = this.getProductById(id);
        p.setAmount(p.getAmount() + amount);
        this.repository.save(p);
        return p.getAmount();
    }
}
