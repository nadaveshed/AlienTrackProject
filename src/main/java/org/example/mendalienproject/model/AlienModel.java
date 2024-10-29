package org.example.mendalienproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="alien_table")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlienModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String weapon;
    private String vehicle;
    private long commanderId;
    private String type;


    public AlienModel(AlienModel alienModel) {
        this.id = alienModel.getId();
        this.name = alienModel.getName();
        this.vehicle = alienModel.getVehicle();
        this.commanderId = alienModel.getCommanderId();
        this.weapon = alienModel.getWeapon();
        this.type = alienModel.getType();
    }
}
