package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Cart;
import models.Products;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-04-18T23:51:57")
@StaticMetamodel(CartItems.class)
public class CartItems_ { 

    public static volatile SingularAttribute<CartItems, Integer> quantity;
    public static volatile SingularAttribute<CartItems, Products> productId;
    public static volatile SingularAttribute<CartItems, Cart> cartId;
    public static volatile SingularAttribute<CartItems, Integer> cartItemId;

}