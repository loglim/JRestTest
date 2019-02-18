package cz.loglim.jrest.res;

import javax.persistence.*;

@Entity
@Table(name = "supplies")
public class Supply {

    @Id
    @Column(name = "item_id", nullable = false)
    protected Long id;

    @Column(name = "warehouse_id", nullable = false)
    protected Long warehouseId;

    @Column(name = "quantity", nullable = false)
    protected int quantity;

    public Long getItemId() {
        return id;
    }

    public void setItemId(Long itemId) {
        this.id = itemId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
