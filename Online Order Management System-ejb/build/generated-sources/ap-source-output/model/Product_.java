package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-20T15:39:46")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, Users> createdBy;
    public static volatile SingularAttribute<Product, Double> price;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, Users> modifiedBy;
    public static volatile SingularAttribute<Product, Long> id;
    public static volatile SingularAttribute<Product, String> category;
    public static volatile SingularAttribute<Product, Integer> stock;
    public static volatile SingularAttribute<Product, String> status;

}