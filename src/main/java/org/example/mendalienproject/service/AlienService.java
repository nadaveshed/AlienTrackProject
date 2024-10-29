package org.example.mendalienproject.service;

import jakarta.annotation.Nonnull;
import org.example.mendalienproject.model.*;
import org.example.mendalienproject.repository.AlienChiefCommanderRepository;
import org.example.mendalienproject.repository.AlienCommanderRepository;
import org.example.mendalienproject.repository.AlienModelRepository;
import org.example.mendalienproject.repository.AlienWarriorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlienService {
    private static final Logger logger = LoggerFactory.getLogger(AlienService.class);

    private final AlienModelRepository alienModelRepository;
    private final AlienWarriorRepository alienWarriorRepository;
    private final AlienCommanderRepository alienCommanderRepository;
    private final AlienChiefCommanderRepository alienChiefCommanderRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public AlienService(
                        @Nonnull AlienModelRepository alienModelRepository,
                        @Nonnull AlienWarriorRepository alienWarriorRepository,
                        @Nonnull AlienCommanderRepository alienCommanderRepository,
                        @Nonnull AlienChiefCommanderRepository alienChiefCommanderRepository,
                        @Nonnull SimpMessagingTemplate messagingTemplate) {
        this.alienModelRepository = alienModelRepository;
        this.alienChiefCommanderRepository = alienChiefCommanderRepository;
        this.alienWarriorRepository = alienWarriorRepository;
        this.alienCommanderRepository = alienCommanderRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public AlienModel addAlien(AlienModel alienModel) {
        validateAlien(alienModel);
        AlienModel addedAlien;
        String alienType = alienModel.getType();

        switch (alienModel.getType()) {
            case "WARRIOR":
                AlienWarrior warrior = new AlienWarrior();
                warrior.setName(alienModel.getName());
                warrior.setWeapon(alienModel.getWeapon());
                warrior.setCommanderId(alienModel.getCommanderId());
                addedAlien = alienWarriorRepository.save(warrior).toAlienModel();
                break;
            case "COMMANDER":
                AlienCommander commander = new AlienCommander();
                commander.setName(alienModel.getName());
                commander.setVehicle(alienModel.getVehicle());
                commander.setCommanderId(alienModel.getCommanderId());
                AlienCommander savedCommander = alienCommanderRepository.save(commander);
                addedAlien = savedCommander.toAlienModel();
                break;
            case "CHIEF_COMMANDER":
                AlienChiefCommander chiefCommander = new AlienChiefCommander();
                chiefCommander.setName(alienModel.getName());
                chiefCommander.setVehicle(alienModel.getVehicle());
                chiefCommander.toAlienModel();
                AlienChiefCommander savedChiefCommander = alienChiefCommanderRepository.save(chiefCommander);
                addedAlien = savedChiefCommander.toAlienModel();
                break;
            default:
                throw new IllegalArgumentException("Unknown alien type: " + alienModel.getType());
        }

        if (addedAlien != null) {
            AlienModel newAlienModel = new AlienModel();
            newAlienModel.setName(addedAlien.getName());
            newAlienModel.setType(alienType);
            newAlienModel.setCommanderId(addedAlien.getCommanderId() != 0 ? addedAlien.getCommanderId() : 0);
            newAlienModel.setWeapon(addedAlien.getWeapon());
            newAlienModel.setVehicle(addedAlien.getVehicle());

            alienModelRepository.save(newAlienModel);
            messagingTemplate.convertAndSend("/alienInfo/alienUpdates", getAllAliens());
        }

        return addedAlien;
    }

    public List<AlienModel> getAllAliens() {
        return alienModelRepository.findAll().stream()
                .map(AlienModel::new)
                .toList();
    }

    private void validateAlien(AlienModel alien) {
        if ("WARRIOR".equals(alien.getType())) {
            if (alien.getCommanderId() != 0) {
                AlienModel commander = alienModelRepository.findById(alien.getCommanderId())
                        .orElseThrow(() -> {
                            String msg = "Invalid Commander ID for WARRIOR: " + alien.getCommanderId() + ". It must exist.";
                            logger.error(msg);
                            return new IllegalArgumentException(msg);
                        });

                if (!"COMMANDER".equals(commander.getType())) {
                    String msg = "WARRIOR can only have a commander of type COMMANDER.";
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }

                long warriorCount = alienModelRepository.countByCommanderId(alien.getCommanderId());
                if (warriorCount >= 10) {
                    String msg = "A commander can manage up to 10 warriors.";
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
            }
        }

        if ("COMMANDER".equals(alien.getType())) {
            if (alien.getCommanderId() != 0) {
                AlienModel chiefCommander = alienModelRepository.findById(alien.getCommanderId())
                        .orElseThrow(() -> {
                            String msg = "Invalid Commander ID for COMMANDER: " + alien.getCommanderId() + ". It must exist.";
                            logger.error(msg);
                            return new IllegalArgumentException(msg);
                        });

                if (!"CHIEF_COMMANDER".equals(chiefCommander.getType())) {
                    String msg = "COMMANDER cannot have a commander ID pointing to anything other than CHIEF_COMMANDER.";
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }

                long commanderCount = alienModelRepository.countByCommanderId(alien.getCommanderId());
                if (commanderCount >= 3) {
                    String msg = "A chief commander can manage up to 3 commanders.";
                    logger.error(msg);
                    throw new IllegalArgumentException(msg);
                }
            }
        }
    }
}
