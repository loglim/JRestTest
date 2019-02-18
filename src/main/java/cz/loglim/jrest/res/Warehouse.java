package cz.loglim.jrest.res;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id", updatable = false, nullable = false)
    protected Long id;

    @Column(name = "capacity", nullable = false)
    protected int capacity;

    @Column(name = "last_access", nullable = false)
    protected Date lastAccess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }
}
