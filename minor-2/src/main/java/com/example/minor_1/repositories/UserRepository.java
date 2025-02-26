package com.example.minor_1.repositories;

import com.example.minor_1.models.SecuredUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SecuredUser,Integer> {

    SecuredUser findByusername(String name);
}
