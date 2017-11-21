package com.pawel.bootstrap;

import com.pawel.domain.Category;
import com.pawel.domain.UnitOfMeasure;
import com.pawel.repositories.CategoryRepository;
import com.pawel.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Pawel on 2017-10-28.
 */
@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootStrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public BootStrapMySQL(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (categoryRepository.count() == 0L) {
			log.debug("loading categories");
			loadCategories();
		}

		if (unitOfMeasureRepository.count() == 0L) {
			log.debug("loading UOMs");
			loadUom();
		}
	}

	private void loadCategories() {
		Category cat1 = new Category();
		cat1.setDescription("American");
		categoryRepository.save(cat1);

		Category cat2 = new Category();
		cat2.setDescription("Italian");
		categoryRepository.save(cat2);

		Category cat3 = new Category();
		cat3.setDescription("Mexican");
		categoryRepository.save(cat3);

		Category cat4 = new Category();
		cat4.setDescription("Fast Food");
		categoryRepository.save(cat4);
	}

	private void loadUom() {
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setDescription("Teaspoon");
		unitOfMeasureRepository.save(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setDescription("Tablespoon");
		unitOfMeasureRepository.save(uom2);

		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setDescription("Cup");
		unitOfMeasureRepository.save(uom3);

		UnitOfMeasure uom4 = new UnitOfMeasure();
		uom4.setDescription("Each");
		unitOfMeasureRepository.save(uom4);
	}
}
