package org.charlie.dontblamedadproject.models.data;


import org.charlie.dontblamedadproject.models.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface JobDao extends CrudRepository<Job, Integer> {
}