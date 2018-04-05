package org.charlie.dontblamedadproject.models.data;


import org.charlie.dontblamedadproject.models.Child;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ChildDao extends CrudRepository<Child, Integer> {
}