package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Delivery;
import model.Orders;
import model.Review;
import model.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-20T15:39:46")
@StaticMetamodel(Payment.class)
public class Payment_ { 

    public static volatile SingularAttribute<Payment, String> createdAt;
    public static volatile SingularAttribute<Payment, Delivery> delivery;
    public static volatile ListAttribute<Payment, Orders> orderss;
    public static volatile SingularAttribute<Payment, Double> dueAmount;
    public static volatile SingularAttribute<Payment, Review> review;
    public static volatile SingularAttribute<Payment, Long> id;
    public static volatile SingularAttribute<Payment, Users> collectedBy;
    public static volatile SingularAttribute<Payment, String> status;
    public static volatile SingularAttribute<Payment, String> payOn;
    public static volatile SingularAttribute<Payment, Double> amountReceived;
    public static volatile SingularAttribute<Payment, Users> customer;

}