package com.taj.demo.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
 * This is a helper class for Thymeleaf;
 * It's send as object inside the ModelView object to easy build checkboxes.
 * Example in AdminController.editUserGet()
 * */
public class Checkbox {
	public String name;
	public int nameId;
	public boolean checked;
	
	public Checkbox(String name) {
		this.name = name;
	}
	
}
