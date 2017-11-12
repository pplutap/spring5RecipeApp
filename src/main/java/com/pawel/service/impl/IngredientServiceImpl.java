package com.pawel.service.impl;

import com.pawel.commands.IngredientCommand;
import com.pawel.converters.IngredientCommandToIngredient;
import com.pawel.converters.IngredientToIngredientCommand;
import com.pawel.domain.Ingredient;
import com.pawel.domain.Recipe;
import com.pawel.repositories.RecipeRepository;
import com.pawel.repositories.UnitOfMeasureRepository;
import com.pawel.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final RecipeRepository recipeRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(IngredientCommandToIngredient ingredientCommandToIngredient,
			IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			log.error("Recipe not found for ingredientId: " + recipeId);
		}

		Recipe recipe = recipeOptional.get();

		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

		if(!ingredientCommandOptional.isPresent()){
			log.error("Ingredient id not found: " + ingredientId);
		}

		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredient(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

		if (!recipeOptional.isPresent()) {
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		}
		Recipe recipe = recipeOptional.get();

		Optional<Ingredient> ingredientOptional = recipe
				.getIngredients().stream()
				.filter(ingredient -> command.getId().equals(ingredient.getId()))
				.findFirst();

		if (ingredientOptional.isPresent()) {
			Ingredient found = ingredientOptional.get();
			found.setDescription(command.getDescription());
			found.setAmount(command.getAmount());
			found.setUnitOfMeasure(unitOfMeasureRepository.findById(command.getUnitOfMeasure().getId()).orElseThrow(() -> new RuntimeException("UOM not found")));
		} else {
			recipe.addIngredient(ingredientCommandToIngredient.convert(command));
		}

		Recipe savedRecipe = recipeRepository.save(recipe);

		return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
			.stream()
			.filter(ingredient ->  ingredient.getId().equals(command.getId())).findFirst().get());
	}
}
