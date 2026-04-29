package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository  extends CrudRepository<Role, String> {
}
