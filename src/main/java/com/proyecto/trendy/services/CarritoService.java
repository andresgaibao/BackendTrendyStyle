package com.proyecto.trendy.services;

import com.proyecto.trendy.controller.CarritoController;
import com.proyecto.trendy.entity.Carrito;
import com.proyecto.trendy.entity.CartItem;
import com.proyecto.trendy.entity.Product;
import com.proyecto.trendy.entity.User;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.CarritoRepository;
import com.proyecto.trendy.repository.CartItemRepository;
import com.proyecto.trendy.repository.ProductRepository;
import com.proyecto.trendy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CarritoService {

    private final UserRepository userRepo;
    private final CarritoRepository repository;

    public CarritoService(UserRepository userRepo, CarritoRepository repository, ProductRepository productRepo, CartItemRepository itenRepo) {
        this.userRepo = userRepo;
        this.repository = repository;
        this.productRepo = productRepo;
        this.itenRepo = itenRepo;
    }

    private  String getAuthUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Transactional
    private Carrito getCar(){
        String username = getAuthUserEmail();
        User user = userRepo.findUserByEmail(username);

        if(user == null){
            throw  new UsernameNotFoundException("el usuario no fue encontrado");
        }
        return  user.getCart();
    }

    // Agregar Producto

    @Autowired
    private final ProductRepository productRepo;
    @Autowired
    private final CartItemRepository itenRepo;

    @Transactional
    public void addProduct(Integer id, int cantidad) throws MyException {
        Product product = productRepo.findProductById(id);

        if(product == null){
            throw new MyException("El producto no pudo ser encontrado");
        }

        Carrito cart = getCar();

        if(cart == null){
            cart = new Carrito();
            User userExist = userRepo.findUserByEmail(getAuthUserEmail());

            userExist.setCart(cart);
            cart.setUser(userExist);

            /*
            En estas dos líneas se genera una conexión mutua para permitir que un único carrito
            esté asociado a un único cliente y el token pueda hacer la validación de forma
            indirecta
             */
        }
        boolean productFound = false;

        //Se itera sobre los productos del carrito para buscar su cantidad actual y sumarle lo que se solicita
        for(CartItem item: cart.getItems()){
            if(item.getProduct().getId().equals(id)){
                item.setPrice(product.getPrice());
                item.setCantidad(item.getCantidad() + cantidad);// en caso de que no se encuentre se envia la cantidad.
                item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getCantidad())));

                productFound = true;
                break;
            }
        }
        //Si no hay ningún producto, se envían los nuevos datos del carrito
        if(!productFound){
            CartItem newItem = new CartItem();

            newItem.setProduct(product);
            newItem.setCantidad(cantidad);
            newItem.setPrice(product.getPrice());
            newItem.setSubtotal(newItem.getPrice().multiply(BigDecimal.valueOf(newItem.getCantidad())));

            itenRepo.save(newItem);//Se guardan los datos en el repositorio.
            cart.getItems().add(newItem);

        }
    }


//..................................................................................
    //                  Método para eliminar un producto del carrito

     /*
    Flujo (.stream()): Secuencia de elementos sobre la cual se pueden ejecutar operaciones de procesamiento
    como por ejemplo buscar información
     */

    @Transactional
    public void  deleteFromCart(Integer id){
        Carrito cart =getCar();

        //Se busca el item del carrito que se va a eliminar
        CartItem itemRemove = cart.getItems().stream()
                .filter(product -> product.getId().equals(id))
        //Esta línea se encarga de utilizar un filtro para buscar el id del item que coincida con el idProduct
                .findAny().orElse(null);
        /*
        Esta línea devuelve la primera coincidencia de Id´s, También puede devolver un null
        en caso de que no existan coincidencias
        */

        //Si la operación anterior fue exitosa, se elimina también el item y su respectivo espacio en el carrito
        if (itemRemove != null) {
            cart.getItems().remove(itemRemove);
        }

        //Se guarda el carrito actualizado y se eliminan los items del carrito
        //Se guarda el carrito actualizado y se eliminan los items del carrito
        repository.save(cart);
        itenRepo.delete(itemRemove);
    }

}
