package com.dharma.spring.security.persistence.dao;

import com.dharma.spring.security.persistence.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    public Organization findByName(String name);

}
