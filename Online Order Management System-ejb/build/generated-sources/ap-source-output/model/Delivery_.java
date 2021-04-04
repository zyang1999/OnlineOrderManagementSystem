package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Payment;
import model.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-20T15:39:46")
@StaticMetamodel(Delivery.class)
public class Delivery_ { 

    public static volatile SingularAttribute<Delivery, Payment> payment;
    public static volatile SingularAttribute<Delivery, Long> id;
    public static volatile SingularAttribute<Delivery, Users> user;
    public static volatile SingularAttribute<Delivery, String> status;

}