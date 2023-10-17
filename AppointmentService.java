package net.murphyshort.appointmentservice;

import java.util.ArrayList;
import java.util.Iterator;

import net.murphyshort.appointmentservice.Appointment.NullArgumentException;

import java.util.Date;

public class AppointmentService {


	//Constants
	public static final int NO_INDEX = -1;

	//Attributes
	private ArrayList<Appointment> appointments;
	private long unique_id_counter;
	
	//Constructors
	AppointmentService()
	{
		appointments = new ArrayList<Appointment>();
		unique_id_counter = 0;
	}

	//Methods
	public void add_appointment(Appointment appointment_in) throws NullArgumentException{
		try{
			appointment_in.set_ID(Long.toString(unique_id_counter++));
		}catch(Appointment.NullArgumentException e) {
			throw e;
		}
		appointments.add(appointment_in);
	}

	//Use an iterator to find an ID in the arrayList
	public int find_ID_index(long id)
	{
		Iterator<Appointment> iter = appointments.iterator();
		int index = 0;
		while (iter.hasNext()){
			if (iter.next().get_ID().equals(Long.toString(id)))
				return index;
			index++;
		}
		
		return NO_INDEX;
	}

	//Use an iterator to find the ID and remove it
	public void remove_appointment(long id)
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			appointments.remove(index);
	}
	
	public void remove_appointment(String id)
	{
		int index = find_ID_index(Long.parseLong(id));
		if (index != NO_INDEX)
			appointments.remove(index);
	}

	/*
	 * The remaining methods are not requirements, 
	 * but are useful
	 */
	
	public void update_date(long id, Date date) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX) {
			try {
				appointments.get(index).set_date(date);
			}catch(Appointment.NullArgumentException e) {
				throw e;
			}
		}
	}

	public void update_description(long id, String desc) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX) {
			try {
				appointments.get(index).set_description(desc);
			}catch(Appointment.NullArgumentException e) {
				throw e;
			}
		}
	}
	
	public int list_size()
	{
		return appointments.size();
	}
	
	//Return the appointment, if it is within the bounds of the array, or else return a default appointment
	public Appointment get_appointment_at(int index) throws NullArgumentException {
		Appointment temp;
		try {
			temp = appointments.get(index);
			return temp;
		}catch(IndexOutOfBoundsException e) {
			try {
			return new Appointment();
			}catch(Appointment.NullArgumentException en) {
				throw en;
			}
		}
	}

}
