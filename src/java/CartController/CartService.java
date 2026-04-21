/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CartController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import models.Accounts;
import models.Cart;
import models.CartItems;
import models.CartItemsJpaController;
import models.CartJpaController;
import models.Products;

/**
 *
 * @author ASUS
 */
public class CartService {

    public void addToSessionCart(HttpSession session, Products product) {
        CartItemsJpaController itemDao = new CartItemsJpaController();

        Map<String, CartItems> cart
                = (Map<String, CartItems>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        String id = product.getProductId();

        if (cart.containsKey(id)) {
            CartItems item = cart.get(id);
            item.setQuantity(item.getQuantity() + 1);
        } else {
            CartItems newItem = new CartItems();
           
            newItem.setProductId(product);
            newItem.setQuantity(1);
            cart.put(id, newItem);
        }

        session.setAttribute("cart", cart);
    }

    public void addToDatabaseCart(Accounts acc, Products product) {

        CartJpaController cartDao = new CartJpaController();
        CartItemsJpaController itemDao = new CartItemsJpaController();

        Cart cart = cartDao.findCartByAccount(acc.getAccount());

        if (cart == null) {

            cart = new Cart();
            cart.setAccount(acc);
            cart.setCreatedDate(new Date());

            cartDao.create(cart);
        }

        CartItems item
                = itemDao.findByCartAndProduct(cart.getCartId(), product.getProductId());

        if (item != null) {

            try {
                item.setQuantity(item.getQuantity() + 1);
                itemDao.edit(item);
            } catch (Exception ex) {
                Logger.getLogger(CartService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            CartItems newItem = new CartItems();
            newItem.setCartId(cart);
            newItem.setProductId(product);
            newItem.setQuantity(1);

            itemDao.create(newItem);
        }
    }

    public void mergeCart(HttpSession session, Accounts acc) {

        Map<String, CartItems> cartSession
                = (Map<String, CartItems>) session.getAttribute("cart");
        if (cartSession == null) {
            return;
        }
        for (CartItems item : cartSession.values()) {
            addToDatabaseCart(acc, item.getProductId());
        }
        session.removeAttribute("cart");
    }
}
