package com.Micro.Services.reviewservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Micro.Services.reviewservice.entities.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

	public List<Review> findByProductId(String productId);

}
