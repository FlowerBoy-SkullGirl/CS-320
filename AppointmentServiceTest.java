package net.murphyshort.appointmentservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;

//Test order has been designed to be essential
@DisplayName("Testing The TestService Class")
class AppointmentServiceTest {

	//Constants
	private static final long milliseconds_Day = 0x5265C0A; //8.64e7 + 10 ms
	private static Date tomorrow = new Date();
	private static Date after_tomorrow = new Date();
	private static Date epoch = new Date();
	private static String valid_desc = "This is a valid description.";
	private static String aux_valid_desc = "This is also a valid description.";
	
	private static int OUT_OF_BOUNDS_INDEX = 999;

	
	//Static class instances
	private static Appointment appointment_one;
	private static Appointment appointment_two;
	
	//Class under test
	private static AppointmentService appointmentservice = new AppointmentService();
	
	@BeforeEach
	//These are rebuilt for each test to ensure that order does not matter
	void build_appointments() {
		try {
		epoch.setTime(1); // This will always be in the past
		tomorrow.setTime(new Date().getTime() + milliseconds_Day);
		after_tomorrow.setTime(tomorrow.getTime() + milliseconds_Day);
		
		appointment_one = new Appointment();
		appointment_one.set_date(tomorrow);
		appointment_one.set_description(valid_desc);
		
		appointment_two = new Appointment();
		appointment_two.set_date(after_tomorrow);
		appointment_two.set_description(valid_desc);
		}catch(Appointment.NullArgumentException e) {
			fail("Failed to build appointments from Null arguments");
		}
	}
	
	@AfterEach
	//Ensure that list is empty after every test so that tests can be run in any order
	//Ideally, the garbage collector will reallocate the abandoned appointmentservice memory
	void reset_service() {
		appointmentservice = new AppointmentService();
	}
	
	//Requirement: add appointments with unique ID's
	@Test
	@DisplayName("Adding appointments")
	void test_add_appointments() {
		try {
		appointmentservice.add_appointment(appointment_one);
		appointmentservice.add_appointment(appointment_two);
		
		Appointment index_zero = appointmentservice.get_appointment_at(0);
		Appointment index_one  = appointmentservice.get_appointment_at(1);
		
		assertAll("Adding appointments",
				() -> assertTrue(index_zero.get_ID() != index_one.get_ID()),
				() -> assertEquals(appointment_one.get_ID(), index_zero.get_ID()), //ensure that the objects are in the list AND match values
				() -> assertEquals(appointment_two.get_ID(), index_one.get_ID())
				);
		}catch(Appointment.NullArgumentException e)
		{
			fail("Null values passed");
		}
	}
	
	//Coverage: update appointment fields
	@Test
	@DisplayName("Updating appointments")
	void test_update_appointment() {
		try {
		appointmentservice.add_appointment(appointment_one);
		Appointment app = appointmentservice.get_appointment_at(0);
		long id_val = Long.parseLong(app.get_ID());
		
		appointmentservice.update_date(id_val, after_tomorrow);
		appointmentservice.update_description(id_val, aux_valid_desc);
		
		assertAll("Updating appointment",
				() -> assertEquals(after_tomorrow, app.get_date()),
				() -> assertEquals(aux_valid_desc, app.get_description())
				);
		} catch(Appointment.NullArgumentException e) {
			fail("Null values passed");
		}
	}
	
	//Requirement: Delete appointments by ID
	@Test
	@DisplayName("Deleting appointments")
	void test_delete_appointments() {
		try {
		appointmentservice.add_appointment(appointment_one);
		appointmentservice.add_appointment(appointment_two);
		
		Appointment app = appointmentservice.get_appointment_at(0);
		Appointment app_two = appointmentservice.get_appointment_at(1);
		
		appointmentservice.remove_appointment(app.get_ID());
		appointmentservice.remove_appointment(Long.parseLong(app_two.get_ID()));
		
		assertAll("Deleting contacts",
				() -> assertEquals("", appointmentservice.get_appointment_at(0).get_ID()),
				() -> assertEquals("", appointmentservice.get_appointment_at(1).get_ID())
				);
		} catch(Appointment.NullArgumentException e) {
			fail("Null values passed");
		}
	}
	
	@Test
	@DisplayName("Test bad appointment query")
	void test_query_empty_list() {
		try {
		assertEquals(new Appointment().get_ID(), appointmentservice.get_appointment_at(OUT_OF_BOUNDS_INDEX).get_ID());
		} catch(Appointment.NullArgumentException e) {
			fail("Null values passed");
		}
	}
	
	@Test
	@DisplayName("Update Non-existent appointments")
	void test_update_empty_appointments() {
		try {
		appointmentservice.update_date(OUT_OF_BOUNDS_INDEX, after_tomorrow);
		appointmentservice.update_description(OUT_OF_BOUNDS_INDEX, aux_valid_desc);
		
		String empty_value = new Appointment().get_description();
		assertAll("Updating appointment",
				() -> assertEquals(empty_value, appointmentservice.get_appointment_at(OUT_OF_BOUNDS_INDEX).get_description()),
				() -> assertEquals(empty_value, appointmentservice.get_appointment_at(OUT_OF_BOUNDS_INDEX).get_ID()),
				() -> assertTrue(new Date().getTime() - milliseconds_Day < appointmentservice.get_appointment_at(OUT_OF_BOUNDS_INDEX).get_date().getTime())
				);
	} catch(Appointment.NullArgumentException e) {
		fail("Null values passed");
	}
	}
}
