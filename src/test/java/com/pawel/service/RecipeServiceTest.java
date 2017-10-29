package com.pawel.service;

import com.pawel.domain.Recipe;
import com.pawel.repositories.RecipeRepository;
import com.pawel.service.impl.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Pawel on 2017-10-29.
 */
public class RecipeServiceTest {

	private RecipeService recipeService;
	
	@Mock
	private RecipeRepository recipeRepository;
	
	@Test
	public void getRecipes() throws Exception {
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);

		when(recipeService.getRecipes()).thenReturn(recipesData);

		Set<Recipe> recipes = recipeService.getRecipes();

		assertEquals(1, recipes.size());

		verify(recipeRepository, times(1)).findAll();
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(recipeRepository);
	}
}
