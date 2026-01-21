package com.yui.clearops.repositories;

import com.yui.clearops.model.entities.Software;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
}
