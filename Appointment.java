package net.murphyshort.appointmentservice;

import java.util.Date;

public class Appointment {

	public class NullArgumentException extends Exception {
		private static final long serialVersionUID = 1L;

		public NullArgumentException(String errorMessage){
			super(errorMessage);
		}
	}
	//Constants
	//Public so that they may be used in tests without maintaining separate constants in the Test class
	public final int MAX_ID_LEN = 10;
	public final int MAX_DESC_LEN = 50;

	//Attributes
	private String appointment_ID;
	private Date date;
	private String description;
	
	//Constructors
	//Requirements state that the fields are required, but not that they should contain characters:
	//Requirement requires validation
	Appointment() throws NullArgumentException
	{
		set_ID("");
		set_date(new Date());
		set_description("");
	}

	//Mutators
	public void set_ID(String id) throws NullArgumentException
	{
		if (id == null)
			throw new NullArgumentException("Null ID");
		//return if an ID is already set
		if (appointment_ID != "" && appointment_ID != null)
			return;
		if (id.length() <= MAX_ID_LEN)
			appointment_ID = id;
		else
			appointment_ID = "";
	}

	public void set_date(Date date_in) throws NullArgumentException
	{
		if (date_in == null)
			throw new NullArgumentException("Null date");
		if (!date_in.before(new Date()))
			date = date_in;
		else
			date = new Date();
	}

	public void set_description(String desc) throws NullArgumentException
	{
		if (desc == null)
			throw new NullArgumentException("Null description");
		if (desc.length() <= MAX_DESC_LEN)
			description = desc;
		else
			description = "";
	}

	//Accessors
	public String get_ID()
	{
		return appointment_ID;
	}
	
	public Date get_date()
	{
		return date;
	}

	public String get_description()
	{
		return description;
	}
}
