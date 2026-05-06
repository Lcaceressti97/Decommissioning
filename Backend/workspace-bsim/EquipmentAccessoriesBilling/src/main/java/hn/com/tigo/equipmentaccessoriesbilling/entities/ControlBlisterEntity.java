package hn.com.tigo.equipmentaccessoriesbilling.entities;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.ControlBlisterModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_CONTROL_BLISTER")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlBlisterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    private Long id;

    @Column(name = "MODEL", length = 100)
    private String model;

    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;

    public ControlBlisterModel entityToModel() {

        ControlBlisterModel model = new ControlBlisterModel();

        model.setId(this.getId());
        model.setModel(this.getModel());
        model.setCreated(this.getCreated());

        return model;
    }

}
