package com.pawel.controllers;

import com.pawel.commands.RecipeCommand;
import com.pawel.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pawel on 2017-11-01.
 */
@Controller
public class RecipeController {

	private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping("/recipe/{id}/show")
	public String getRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		return "recipe/show";
	}

	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());

		return "recipe/recipeform";
	}

	@PostMapping
	@RequestMapping("recipe")
	public String  saveOrUpdate(@ModelAttribute RecipeCommand command) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}

	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findByCommandId(Long.valueOf(id)));
		return "recipe/recipeform";
	}
}
