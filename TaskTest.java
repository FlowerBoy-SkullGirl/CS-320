package net.murphyshort.TaskService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing the Task class")
class TaskTest {
	//Constants
	private static final String NULL_STRING = null;
	private static final String id_value = "1";
	
	//Class under test instance
	private static Task task;
	
	//Boundary values
	private static String id_len = "";
	private static String name_len = "";
	private static String desc_len = "";
	
	//Necessary environment
	@BeforeAll
	static void build_boundary_values()
	{
		try {
			task = new Task();
		}catch(Task.NullArgumentException e) {
			fail("Constructor failed: null");
			return;
		}
		
		for (int i = 0; i < task.MAX_NAME_LEN; i ++) {
			name_len += 'A';
		}
		for (int i = 0; i < task.MAX_DESC_LEN; i ++) {
			desc_len += 'S';
		}
		for (int i = 0; i < task.MAX_ID_LEN; i ++) {
			id_len += id_value;
		}
	}
	
	//Requirement: The fields shall not be null
	@Test
	@DisplayName("Testing null mutations")
	void test_invalid_mutations_null() {
		try {
			assertAll("Task null arguments",
					() -> assertThrows(Task.NullArgumentException.class, () -> task.set_ID(NULL_STRING)),
					() -> assertThrows(Task.NullArgumentException.class, () -> task.set_name(NULL_STRING)),
					() -> assertThrows(Task.NullArgumentException.class, () -> task.set_description(NULL_STRING))
					);
		}catch(Exception e) {
			fail("Failed to construct test case");
		}
	}
	
	//Requirement: The fields are updatable within a defined length
	@Test
	@DisplayName("Valid mutations")
	void test_valid_mutations_length() {
		try {
			
		task.set_name(name_len);
		task.set_description(desc_len);
		
		assertAll("Valid mutations",
				() -> assertEquals(name_len, task.get_name()),
				() -> assertEquals(desc_len, task.get_description())
		);
		
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: The fields cannot be over a defined certain length
	@Test
	@DisplayName("Mutations of invalid length")
	void test_invalid_mutations_length() {
		try {
		//Values over the boundary
		task.set_name(name_len + 'A');
		task.set_description(desc_len + 'A');
		
		assertAll("Task mutation lengths over bounds",
				() -> assertEquals("", task.get_name()),
				() -> assertEquals("", task.get_description())
		);
	
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: ID must be within a defined length
	//Requirement: ID shall not be updatable.
	@Test
	@DisplayName("Invalid reassignment of ID values")
	void test_reassign_ID() {	
		try {
		
		task.set_ID(id_len);
		assertEquals(id_len, task.get_ID());
		
		//Create a different string value in case MAX_ID_LEN = 1
		long id_value_two = Long.parseLong(id_value);
		id_value_two++;
	
		task.set_ID(Long.toString(id_value_two));
		assertEquals(id_len, task.get_ID());
		
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: ID shall not be longer than a defined length
	@Test
	@DisplayName("ID values of invalid length")
	void test_invalid_ID_assignment() {
		try {
			
		Task second_task = new Task(); //necessary because the class other instance may have already been assigned an ID
		second_task.set_ID(id_len + id_value);
		assertEquals("", second_task.get_ID());
		
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}

}
