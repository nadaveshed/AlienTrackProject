package org.example.mendalienproject.repository;

import org.example.mendalienproject.model.AlienModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlienModelRepository extends JpaRepository<AlienModel, Long> {
    long countByCommanderId(Long commanderId);
}
