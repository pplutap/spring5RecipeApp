package com.pawel.controllers;

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

	public IngredientController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping
	@RequestMapping("recipe/{id}/ingredients")
	public String showIngredients(@PathVariable String id, Model model) {
		log.debug("Getting ingredient list for recipe id: " + id);
		model.addAttribute("recipe", recipeService.findByCommandId(Long.valueOf(id)));
		return "recipe/ingredient/list";
	}
}
