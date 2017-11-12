package com.pawel.controllers;

import com.pawel.commands.IngredientCommand;
import com.pawel.service.IngredientService;
import com.pawel.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IngredientController {

	private RecipeService recipeService;
	private IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping("recipe/{id}/ingredients")
	public String showIngredients(@PathVariable String id, Model model) {
		log.debug("Getting ingredient list for recipe id: " + id);
		model.addAttribute("recipe", recipeService.findByCommandId(Long.valueOf(id)));
		return "recipe/ingredient/list";
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("Getting ingredient for recipe id: " + recipeId + " , ingredient id: " + id);
		IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id));
		model.addAttribute("ingredient", command);
		return "recipe/ingredient/show";
	}
}
