package com.pawel.repositories;

import com.pawel.domain.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Pawel on 2017-10-28.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
