package com.sdp.petapi.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class PetTest {

	@Test
	public void create() throws Exception {
		Date buddy_got_here = new Date();
		String[] pics_of_buddy = { "walking in the park", "catching a frisbee", "biting the neighbors kid..." };
		Pet pe = new Pet("Buddy", "dog", "M", "old_af", "medium", 123.4, buddy_got_here,
				"He is very wet. Just like all the time", pics_of_buddy, false);

		assertEquals(pe.getName(), "Buddy");
		assertNotEquals(pe, null);
		assertEquals(pe.getType(), "dog");

	}

}
