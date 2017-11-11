package com.pawel.controllers;

import com.pawel.commands.RecipeCommand;
import com.pawel.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pawel on 2017-11-01.
 */
@Controller
@Slf4j
public class RecipeController {

	private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String getRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(new Long(id)));
		return "recipe/show";
	}

	@GetMapping
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

	@GetMapping
	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findByCommandId(Long.valueOf(id)));
		return "recipe/recipeform";
	}

	@GetMapping
	@RequestMapping("recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		log.debug("Deleting: " + id);
		recipeService.deleteById(Long.valueOf(id));
		return "redirect:/";
	}
}
