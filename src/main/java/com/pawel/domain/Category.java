package com.pawel.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Created by Pawel on 2017-10-28.
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@ManyToMany(mappedBy = "categories")
	private Set<Recipe> recipes;

}
