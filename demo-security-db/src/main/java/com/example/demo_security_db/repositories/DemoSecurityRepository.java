package com.example.demo_security_db.repositories;

import com.example.demo_security_db.models.DemoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoSecurityRepository extends JpaRepository<DemoUser,Integer> {

    DemoUser findByUsername(String u);

}
