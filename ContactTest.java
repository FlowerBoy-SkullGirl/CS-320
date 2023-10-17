package net.murphyshort.ContactTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) test order is nonessential
@DisplayName("Testing the Contact Class")
class ContactTest {
	//Constants
	private static final String NULL_STRING = null;
	private static final String id_value = "1";
	
	//Class under test instance
	private static Contact contact;
	
	//Boundary values
	private static String id_len = "";
	private static String name_len = "";
	private static String phone_len = "";
	private static String phone_letters = "";
	private static String addr_len = "";
	
	//Necessary environment
	@BeforeAll
	static void build_boundary_values()
	{
		try {
			contact = new Contact();
		}catch(Contact.NullArgumentException e) {
			fail("Built null contact");
			return;
		}
		
		for (int i = 0; i < contact.MAX_NAME_LEN; i ++) {
			name_len += 'A';
		}
		for (int i = 0; i < contact.MAX_PHONE_LEN; i ++) {
			phone_len += '1';
			phone_letters += 'A';
		}
		for (int i = 0; i < contact.MAX_ADDR_LEN; i ++) {
			addr_len += 'S';
		}
		for (int i = 0; i < contact.MAX_ID_LEN; i ++) {
			id_len += id_value;
		}
	}
	
	//Requirement: The fields shall not be null
	@Test
	@DisplayName("Testing null mutations")
	void test_invalid_mutations_null() {
		try {
		assertAll("Null contacts",
				() -> assertThrows(Contact.NullArgumentException.class, () -> contact.set_ID(NULL_STRING)),
				() -> assertThrows(Contact.NullArgumentException.class, () -> contact.set_firstName(NULL_STRING)),
				() -> assertThrows(Contact.NullArgumentException.class, () -> contact.set_lastName(NULL_STRING)),
				() -> assertThrows(Contact.NullArgumentException.class, () -> contact.set_phone(NULL_STRING)),
				() -> assertThrows(Contact.NullArgumentException.class, () -> contact.set_address(NULL_STRING))
				);
			}catch(Exception e){
				fail("Constructor error");
			}
	}
	
	//Requirement: The fields are updatable within a defined length
	@Test
	@DisplayName("Valid mutations")
	void test_valid_mutations_length() {
		try {
		contact.set_firstName(name_len);
		contact.set_lastName(name_len);
		contact.set_phone(phone_len);
		contact.set_address(addr_len);
		
		assertAll("Contact",
				() -> assertEquals(name_len, contact.get_firstName()),
				() -> assertEquals(name_len, contact.get_lastName()),
				() -> assertEquals(phone_len, contact.get_phone()),
				() -> assertEquals(addr_len, contact.get_address())
		);
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: The fields cannot be over a defined certain length
	//Requirement: Phone field must be exactly the defined length
	@Test
	@DisplayName("Mutations of invalid length")
	void test_invalid_mutations_length() {
		try {
		//Values over the boundary
		contact.set_firstName(name_len + 'A');
		contact.set_lastName(name_len + 'A');
		contact.set_phone(phone_len + '1');
		contact.set_address(addr_len + 'S');
		
		assertAll("Contact mutation lengths over bounds",
				() -> assertEquals("", contact.get_firstName()),
				() -> assertEquals("", contact.get_lastName()),
				() -> assertEquals("", contact.get_phone()),
				() -> assertEquals("", contact.get_address())
		);
		
		//Values below the boundary
		contact.set_phone(phone_len.substring(0, phone_len.length() - 1));
		assertEquals("", contact.get_phone());
		
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: Phone field shall be digits
	@Test
	@DisplayName("Phone numbers with invalid characters")
	void test_invalid_character_phone() {
		try {
		
		contact.set_phone(phone_letters);
		assertEquals("", contact.get_phone());
		
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: ID must be within a defined length
	//Requirement: ID shall not be updatable.
	@Test
	@DisplayName("Invalid reassignment of ID values")
	void test_reassign_ID() {	
		try {
		contact.set_ID(id_len);
		assertEquals(id_len, contact.get_ID());
		
		//Create a different string value in case MAX_ID_LEN = 1
		long id_value_two = Long.parseLong(id_value);
		id_value_two++;
	
		contact.set_ID(Long.toString(id_value_two));
		assertEquals(id_len, contact.get_ID());
		
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}
	
	//Requirement: ID shall not be longer than a defined length
	@Test
	@DisplayName("ID values of invalid length")
	void test_invalid_ID_assignment() {
		try {
		Contact second_contact = new Contact(); //necessary because the class other instance may have already been assigned an ID
		second_contact.set_ID(id_len + id_value);
		assertEquals("", second_contact.get_ID());
		
		}catch(Contact.NullArgumentException e) {
			fail("Null arguments passed");
		}
	}

}
