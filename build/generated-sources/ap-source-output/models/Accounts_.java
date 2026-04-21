package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Cart;
import models.Orders;
import models.Products;
import models.UserViews;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-04-18T23:51:57")
@StaticMetamodel(Accounts.class)
public class Accounts_ { 

    public static volatile SingularAttribute<Accounts, Date> birthday;
    public static volatile SingularAttribute<Accounts, Boolean> isUse;
    public static volatile SingularAttribute<Accounts, String> lastName;
    public static volatile SingularAttribute<Accounts, Boolean> gender;
    public static volatile SingularAttribute<Accounts, String> pass;
    public static volatile CollectionAttribute<Accounts, Orders> ordersCollection;
    public static volatile SingularAttribute<Accounts, Integer> roleInSystem;
    public static volatile CollectionAttribute<Accounts, Cart> cartCollection;
    public static volatile SingularAttribute<Accounts, String> firstName;
    public static volatile SingularAttribute<Accounts, String> phone;
    public static volatile CollectionAttribute<Accounts, Products> productsCollection;
    public static volatile CollectionAttribute<Accounts, UserViews> userViewsCollection;
    public static volatile SingularAttribute<Accounts, String> account;

}