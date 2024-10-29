package org.example.mendalienproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alien_warrior_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlienWarrior {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long commanderId;
    private String weapon;

    public AlienModel toAlienModel() {
        AlienModel alienModel = new AlienModel();
        alienModel.setId(this.id);
        alienModel.setName(this.name);
        alienModel.setWeapon(this.weapon);
        alienModel.setCommanderId(this.commanderId);
        alienModel.setType("WARRIOR");
        return alienModel;
    }
}
