package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Accounts;
import models.CartItems;
import models.Categories;
import models.OrderDetails;
import models.UserViews;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-04-18T23:51:57")
@StaticMetamodel(Products.class)
public class Products_ { 

    public static volatile SingularAttribute<Products, String> brief;
    public static volatile SingularAttribute<Products, String> productId;
    public static volatile CollectionAttribute<Products, OrderDetails> orderDetailsCollection;
    public static volatile SingularAttribute<Products, Integer> discount;
    public static volatile SingularAttribute<Products, String> productName;
    public static volatile CollectionAttribute<Products, CartItems> cartItemsCollection;
    public static volatile SingularAttribute<Products, Date> postedDate;
    public static volatile SingularAttribute<Products, String> unit;
    public static volatile SingularAttribute<Products, String> productImage;
    public static volatile CollectionAttribute<Products, UserViews> userViewsCollection;
    public static volatile SingularAttribute<Products, Integer> price;
    public static volatile SingularAttribute<Products, Categories> typeId;
    public static volatile SingularAttribute<Products, Accounts> account;

}