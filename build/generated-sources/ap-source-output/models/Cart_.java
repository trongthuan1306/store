package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Accounts;
import models.CartItems;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-04-18T23:51:57")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Date> createdDate;
    public static volatile SingularAttribute<Cart, Integer> cartId;
    public static volatile SingularAttribute<Cart, Accounts> account;
    public static volatile CollectionAttribute<Cart, CartItems> cartItemsCollection;

}