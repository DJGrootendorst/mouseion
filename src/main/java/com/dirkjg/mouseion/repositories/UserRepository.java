package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
