package com.mftplus.springbootocr.repository;

import com.mftplus.springbootocr.entity.ImageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository  extends MongoRepository<ImageEntity, String> {
}
