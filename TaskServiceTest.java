package net.murphyshort.TaskService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//Test order has been designed to be essential
@DisplayName("Testing The TestService Class")
class TaskServiceTest {
	
	//Constants
	private static String valid_name = "John";
	private static String valid_desc = "This is a valid description.";
	private static String aux_valid_name = "Johns";
	private static String aux_valid_desc = "This is also a valid description.";
	
	private static int OUT_OF_BOUNDS_INDEX = 999;

	
	//Static class instances
	private static Task task_one;
	private static Task task_two;
	
	//Class under test
	private static TaskService taskservice = new TaskService();
	
	@BeforeEach
	void build_tasks() {
		try {
		task_one = new Task();
		task_one.set_name(valid_name);
		task_one.set_description(valid_desc);
		
		task_two = new Task();
		task_two.set_name(valid_name);
		task_two.set_description(valid_desc);
		}catch(Task.NullArgumentException e) {
			fail("Null arguments constructor");
			return;
		}
	}
	
	@AfterEach
	//Ensure that list is empty after every test so that tests can be run in any order
	//Ideally, the garbage collector will reallocate the abandoned taskservice memory
	void reset_service() {
		taskservice = new TaskService();
	}
	
	//Requirement: add tasks with unique ID's
	@Test
	@DisplayName("Adding tasks")
	void test_add_tasks() {
		try {
		taskservice.add_task(task_one);
		taskservice.add_task(task_two);
		
		Task index_zero = taskservice.get_task_at(0);
		Task index_one  = taskservice.get_task_at(1);
		
		assertAll("Adding tasks",
				() -> assertEquals(task_one.get_ID(), index_zero.get_ID()),
				() -> assertEquals(task_two.get_ID(), index_one.get_ID()),
				() -> assertTrue(index_zero.get_ID() != index_one.get_ID())
				);
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: update task fields
	@Test
	@DisplayName("Updating tasks")
	void test_update_task() {
		try {
			
		taskservice.add_task(task_one);
		Task con = taskservice.get_task_at(0);
		long id_val = Long.parseLong(con.get_ID());
		
		taskservice.update_name(id_val, aux_valid_name);
		taskservice.update_description(id_val, aux_valid_desc);
		
		assertAll("Updating task",
				() -> assertEquals(aux_valid_name, con.get_name()),
				() -> assertEquals(aux_valid_desc, con.get_description())
				);
		
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: Delete tasks by ID
	@Test
	@DisplayName("Deleting tasks")
	void test_delete_tasks() {
		try {
			
		taskservice.add_task(task_one);
		taskservice.add_task(task_two);
		
		Task con = taskservice.get_task_at(0);
		Task con_two = taskservice.get_task_at(1);
		
		taskservice.remove_task(con.get_ID());
		taskservice.remove_task(Long.parseLong(con_two.get_ID()));
		
		assertEquals(TaskService.NO_INDEX, taskservice.find_ID_index(Long.parseLong(task_one.get_ID())));
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	@Test
	@DisplayName("Test bad task query")
	void test_query_empty_list() {
		try {
		assertEquals(new Task().get_ID(), taskservice.get_task_at(OUT_OF_BOUNDS_INDEX).get_ID());
		
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	@Test
	@DisplayName("Update Non-existent tasks")
	void test_update_empty_tasks() {
		try {
		taskservice.update_name(OUT_OF_BOUNDS_INDEX, aux_valid_name);
		taskservice.update_description(OUT_OF_BOUNDS_INDEX, aux_valid_desc);
		
		String empty_value = new Task().get_name();
		assertAll("Updating task",
				() -> assertEquals(empty_value, taskservice.get_task_at(OUT_OF_BOUNDS_INDEX).get_description()),
				() -> assertEquals(empty_value, taskservice.get_task_at(OUT_OF_BOUNDS_INDEX).get_description()),
				() -> assertEquals(empty_value, taskservice.get_task_at(OUT_OF_BOUNDS_INDEX).get_description()),
				() -> assertEquals(empty_value, taskservice.get_task_at(OUT_OF_BOUNDS_INDEX).get_description())
				);
		}catch(Task.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
		
}
