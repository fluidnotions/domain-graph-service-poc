package com.fluidnotions.databases.stackunderflow.respoitory;

import com.fluidnotions.databases.stackunderflow.entity.UserzToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserzTokenRepository extends CrudRepository<UserzToken, UUID> {
}
