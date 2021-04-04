package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Payment;
import model.Product;
import model.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-20T15:39:46")
@StaticMetamodel(Orders.class)
public class Orders_ { 

    public static volatile SingularAttribute<Orders, Product> product;
    public static volatile SingularAttribute<Orders, Integer> quantity;
    public static volatile SingularAttribute<Orders, String> address;
    public static volatile SingularAttribute<Orders, Double> subtotal;
    public static volatile SingularAttribute<Orders, Payment> payment;
    public static volatile SingularAttribute<Orders, Long> id;
    public static volatile SingularAttribute<Orders, Users> user;
    public static volatile SingularAttribute<Orders, String> status;

}