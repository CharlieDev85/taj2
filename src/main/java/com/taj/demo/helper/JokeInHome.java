package com.taj.demo.helper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JokeInHome {
	private int jokeId;
	private String title;
	private List <String> categories;
	private String author;
	private String body;
	private Double avgRating;
	private Integer myRate;
}
