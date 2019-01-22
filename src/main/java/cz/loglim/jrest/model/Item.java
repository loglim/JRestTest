package cz.loglim.jrest.model;

import javax.persistence.*;

@Entity
@Table(name = "sklad1")
public class Item {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    protected Long id;

    @Column(name = "Price", nullable = false)
    protected double price;

    @Column(name = "ItemName", nullable = false)
    protected String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
