package com.dharma.spring.security.persistence.dao;

import com.dharma.spring.security.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    public Privilege findByName(String name);

}
