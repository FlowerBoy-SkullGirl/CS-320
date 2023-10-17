package net.murphyshort.appointmentservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing the Appointment class")
class AppointmentTest {

	//Constants
	private static final String NULL_STRING = null;
	private static final String id_value = "1";
	private static final long milliseconds_Day = 0x5265C0A; //8.64e7 + 10 ms
	
	
	//Class under test instance
	private static Appointment appointment;
	
	//Boundary values
	private static String id_len = "";
	private static String desc_len = "";
	private static Date epoch = new Date(); //This will be set to Jan 1, 1970 by the build_boundary_value method
	private static Date tomorrow = new Date();
	
	//Necessary environment
	@BeforeAll
	static void build_boundary_values()
	{
		try {
			appointment = new Appointment();
		}catch(Appointment.NullArgumentException e) {
			fail("Null constructor argument");
		}
		
		epoch.setTime(1); // This will always be in the past
		tomorrow.setTime(new Date().getTime() + milliseconds_Day);
		
		for (int i = 0; i < appointment.MAX_DESC_LEN; i ++) {
			desc_len += 'S';
		}
		for (int i = 0; i < appointment.MAX_ID_LEN; i ++) {
			id_len += id_value;
		}
	}
	
	//Requirement: The fields shall not be null
	@Test
	@DisplayName("Testing null mutations")
	void test_invalid_mutations_null() {
		try {
			Appointment second_appointment = new Appointment(); //necessary because the class other instance may have already been assigned an ID
		
		assertAll("Null appointments",
			() -> assertThrows(Appointment.NullArgumentException.class, () -> second_appointment.set_ID(null)),
			() -> assertThrows(Appointment.NullArgumentException.class, () -> appointment.set_date(null)),
			() -> assertThrows(Appointment.NullArgumentException.class, () -> appointment.set_description(NULL_STRING))
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
			appointment.set_date(tomorrow);
			appointment.set_description(desc_len);
		
			assertAll("Valid mutations for appointments",
					() -> assertEquals(tomorrow, appointment.get_date()),
					() -> assertEquals(desc_len, appointment.get_description())
					);
		}catch(Appointment.NullArgumentException e) {
			fail("Null argument was thrown for valid mutations");
		}
	}
	
	//Requirement: The description field cannot be over a defined certain length
	//Requirement: ID shall not be longer than a defined length
	@Test
	@DisplayName("Mutations of invalid length")
	void test_invalid_mutations_length() {
		//Values over the boundary
		try {
			appointment.set_description(desc_len + 'A');
		
			Appointment second_appointment = new Appointment(); //necessary because the class other instance may have already been assigned an ID
			second_appointment.set_ID(id_len + id_value);

			assertAll("Apptmnt mutation lengths over bounds",
					() -> assertEquals("", second_appointment.get_ID()),
					() -> assertEquals("", appointment.get_description())
					);
		}catch(Appointment.NullArgumentException e) {
			fail("Null argument when arguments are not null");
		}
	}
	
	//Requirement: Date field cannot be in the past
	@Test
	@DisplayName("Invalid past date assignment")
	void test_past_dates() {
		try {
			appointment.set_date(epoch); //this date is always in the past
		/*
		 * If the appointment date is greater than yesterday:
		 * This is done instead of comparing it to the current date 
		 * because if the appointment is set to take place immediately(not in the past), 
		 * then comparing the current date may exclude appointments that were set validly
		 */
		assertTrue(new Date().getTime() - milliseconds_Day < appointment.get_date().getTime());
		}catch(Appointment.NullArgumentException e) {
			fail("Null argument when argument is not null");
		}
	}
	
	//Requirement: ID must be within a defined length
	//Requirement: ID shall not be updatable.
	@Test
	@DisplayName("Invalid reassignment of ID values")
	void test_reassign_ID() {	
		try {
			appointment.set_ID(id_len);
			assertEquals(id_len, appointment.get_ID());
		
			//Create a different string value in case MAX_ID_LEN = 1
			long id_value_two = Long.parseLong(id_value);
			id_value_two++;
	
			appointment.set_ID(Long.toString(id_value_two));
			assertEquals(id_len, appointment.get_ID());
		}catch(Appointment.NullArgumentException e) {
			fail("Null exception when arguments are not null");
		}
	}

}
