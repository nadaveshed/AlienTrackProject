package org.example.mendalienproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "alien_chief_commander")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlienChiefCommander {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String vehicle;
    private String name;

    @OneToMany(mappedBy = "commanderId")
    private List<AlienCommander> commanders;

    public AlienModel toAlienModel() {
        AlienModel alienModel = new AlienModel();
        alienModel.setId(this.id);
        alienModel.setName(this.name);
        alienModel.setVehicle(this.vehicle);
        alienModel.setCommanderId(0);
        alienModel.setType("CHIEF_COMMANDER");
        return alienModel;
    }
}