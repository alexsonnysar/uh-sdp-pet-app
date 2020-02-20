package com.sdp.petapi;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
public @Data class Pet {
	private String id;
    private String name;

}
