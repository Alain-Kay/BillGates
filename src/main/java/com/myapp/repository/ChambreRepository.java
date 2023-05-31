package com.myapp.repository;

import com.myapp.domain.Chambre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Chambre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {}
