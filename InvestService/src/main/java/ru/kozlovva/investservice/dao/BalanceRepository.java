package ru.kozlovva.investservice.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends MongoRepository<BalanceInfo, ObjectId> {
    BalanceInfo findFirstByOrderByDateDesc();
}
