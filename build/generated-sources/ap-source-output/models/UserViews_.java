package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Accounts;
import models.Products;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2026-04-18T23:51:57")
@StaticMetamodel(UserViews.class)
public class UserViews_ { 

    public static volatile SingularAttribute<UserViews, Integer> viewId;
    public static volatile SingularAttribute<UserViews, Products> productId;
    public static volatile SingularAttribute<UserViews, Date> viewTime;
    public static volatile SingularAttribute<UserViews, Accounts> account;

}