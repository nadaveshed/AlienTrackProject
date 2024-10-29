package org.example.mendalienproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "alien_commander_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlienCommander {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long commanderId;
    private String vehicle;
    private String name;

    @OneToMany(mappedBy = "commanderId")
    private List<AlienWarrior> warriors;

    public AlienModel toAlienModel() {
        AlienModel alienModel = new AlienModel();
        alienModel.setId(this.id);
        alienModel.setName(this.name);
        alienModel.setVehicle(this.vehicle);
        alienModel.setCommanderId(this.commanderId);
        alienModel.setType("COMMANDER");
        return alienModel;
    }
}