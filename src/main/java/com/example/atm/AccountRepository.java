package com.example.atm;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by student on 6/23/17.
 */

public interface AccountRepository extends CrudRepository<Account, Integer> {
}
