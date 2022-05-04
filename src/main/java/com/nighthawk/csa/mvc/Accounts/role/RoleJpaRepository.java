package com.nighthawk.csa.mvc.Accounts.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  RoleJpaRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}


