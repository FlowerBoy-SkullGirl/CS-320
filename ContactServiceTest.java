package net.murphyshort.ContactTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.*;

@DisplayName("The Contact Service Class")
class ContactServiceTest {

	//Constants
	private static String valid_name = "John";
	private static String valid_phone = "6666666666";
	private static String valid_addr = "1st St";
	private static String aux_valid_name = "Johns";
	private static String aux_valid_phone = "6666666667";
	private static String aux_valid_addr = "2nd St";
	
	private static int OUT_OF_BOUNDS_INDEX = 999;

	
	//Static class instances
	private static Contact contact_one;
	private static Contact contact_two;
	
	//Class under test
	private static ContactService contactservice = new ContactService();
	
	@BeforeEach
	void build_contacts() {
		try {
			
		contact_one = new Contact();
		contact_one.set_firstName(valid_name);
		contact_one.set_lastName(valid_name);
		contact_one.set_phone(valid_phone);
		contact_one.set_address(valid_addr);
		
		contact_two = new Contact();
		contact_two.set_firstName(valid_name);
		contact_two.set_lastName(valid_name);
		contact_two.set_phone(valid_phone);
		contact_two.set_address(valid_addr);
		
		}catch(Contact.NullArgumentException e) {
			fail("Constructor error");
		}
	}
	
	@AfterEach
	//Ensure that list is empty after every test so that tests can be run in any order
	//Ideally, the garbage collector will reallocate the abandoned contactservice memory
	void reset_service() {
		contactservice = new ContactService();
	}
	
	//Requirement: add contacts with unique ID's
	@Test
	@DisplayName("Adding contacts")
	void test_add_contacts() {
		try {
		contactservice.add_contact(contact_one);
		contactservice.add_contact(contact_two);
		
		Contact index_zero = contactservice.get_contact_at(0);
		Contact index_one  = contactservice.get_contact_at(1);
		
		assertAll("Adding contacts",
				() -> assertEquals(contact_one.get_ID(), contactservice.get_contact_at(0).get_ID()),
				() -> assertEquals(contact_two.get_ID(), contactservice.get_contact_at(1).get_ID()),
				() -> assertTrue(index_zero.get_ID() != index_one.get_ID())
				);
		
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: update contact fields
	@Test
	@DisplayName("Updating contacts")
	void test_update_contact() {
		try {
		contactservice.add_contact(contact_one);
		
		Contact con = contactservice.get_contact_at(0);
		long id_val = Long.parseLong(con.get_ID());
		
		contactservice.update_firstName(id_val, aux_valid_name);
		contactservice.update_lastName(id_val, aux_valid_name);
		contactservice.update_phone(id_val, aux_valid_phone);
		contactservice.update_address(id_val, aux_valid_addr);
		
		assertAll("Updating contact",
				() -> assertEquals(aux_valid_name, con.get_firstName()),
				() -> assertEquals(aux_valid_name, con.get_lastName()),
				() -> assertEquals(aux_valid_phone, con.get_phone()),
				() -> assertEquals(aux_valid_addr, con.get_address())
				);
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: Delete contacts by ID
	@Test
	@DisplayName("Deleting Contacts")
	void test_delete_contacts() {
		try {
		contactservice.add_contact(contact_one);
		contactservice.add_contact(contact_two);
		
		Contact con = contactservice.get_contact_at(0);
		Contact con_two = contactservice.get_contact_at(1);
		
		contactservice.remove_contact(con.get_ID());
		contactservice.remove_contact(Long.parseLong(con_two.get_ID()));
		
		assertAll("Removed contacts",
				() -> assertEquals("", contactservice.get_contact_at(0).get_ID()),
				() -> assertEquals("", contactservice.get_contact_at(1).get_ID())
		);
		
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	@Test
	@DisplayName("Test bad contact query")
	void test_query_empty_list() {
		try {
		assertEquals(new Contact().get_ID(), contactservice.get_contact_at(OUT_OF_BOUNDS_INDEX).get_ID());
		
		}catch(Contact.NullArgumentException e) {
			fail("Null value query");
		}
	}
	
	@Test
	@DisplayName("Update Non-existent Contacts")
	void test_update_empty_contacts() {
		try {
			
		contactservice.update_firstName(OUT_OF_BOUNDS_INDEX, aux_valid_name);
		contactservice.update_lastName(OUT_OF_BOUNDS_INDEX, aux_valid_name);
		contactservice.update_phone(OUT_OF_BOUNDS_INDEX, aux_valid_phone);
		contactservice.update_address(OUT_OF_BOUNDS_INDEX, aux_valid_addr);
		
		String empty_value = new Contact().get_firstName();
		assertAll("Updating contact",
				() -> assertEquals(empty_value, contactservice.get_contact_at(OUT_OF_BOUNDS_INDEX).get_address()),
				() -> assertEquals(empty_value, contactservice.get_contact_at(OUT_OF_BOUNDS_INDEX).get_address()),
				() -> assertEquals(empty_value, contactservice.get_contact_at(OUT_OF_BOUNDS_INDEX).get_address()),
				() -> assertEquals(empty_value, contactservice.get_contact_at(OUT_OF_BOUNDS_INDEX).get_address())
				);
		}catch(Contact.NullArgumentException e) {
			fail("Null value query");
		}
	}
}
