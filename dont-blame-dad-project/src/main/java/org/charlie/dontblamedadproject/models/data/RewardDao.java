package org.charlie.dontblamedadproject.models.data;


import org.charlie.dontblamedadproject.models.Reward;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RewardDao extends CrudRepository<Reward, Integer> {
}