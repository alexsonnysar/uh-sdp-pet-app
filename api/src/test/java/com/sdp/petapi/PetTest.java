package com.sdp.petapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetTest {

	@Test
	public void create() throws Exception {
		Pet pe = new Pet("1", "Buddy");
		assertEquals(pe.getName(), "Buddy");

	}
}
