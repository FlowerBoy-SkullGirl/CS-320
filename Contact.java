package net.murphyshort.ContactTests;

/*
 * An object that represents a contact that is assigned an ID by a 
 * contact service and may have its names, phone number,
 * and address fields modified
 */
public class Contact
{
	public class NullArgumentException extends Exception {
		private static final long serialVersionUID = 1L;

		public NullArgumentException(String errorMessage){
			super(errorMessage);
		}
	}
	//Constants
	//Public so that they may be used in tests without maintaining separate constants in the Test class
	public final int MAX_ID_LEN = 10;
	public final int MAX_NAME_LEN = 10;
	public final int MAX_PHONE_LEN = 10;
	public final int MAX_ADDR_LEN = 30;

	//Attributes
	private String contact_ID;
	private String firstName;
	private String lastName;
	private String phone_number;
	private String street_addr;

	//Constructors
	//Requirements state that the fields are required, but not that they should contain characters:
	//Requirement requires validation
	Contact() throws NullArgumentException
	{
		set_ID("");
		set_firstName("");
		set_lastName("");
		set_phone("");
		set_address("");
	}

	//Mutators
	public void set_ID(String id) throws NullArgumentException
	{
		if (id == null)
			throw new NullArgumentException("Null ID");
		//return if an ID is already set
		if (contact_ID != "" && contact_ID != null)
			return;
		if (id.length() <= MAX_ID_LEN)
			contact_ID = id;
		else
			contact_ID = "";
	}

	public void set_firstName(String name) throws NullArgumentException
	{
		if (name == null)
			throw new NullArgumentException("Null Name");
		if (name.length() <= MAX_NAME_LEN)
			firstName = name;
		else
			firstName = "";
	}

	public void set_lastName(String name) throws NullArgumentException
	{
		if (name == null)
			throw new NullArgumentException("Null Name");
		if (name.length() <= MAX_NAME_LEN)
			lastName = name;
		else
			lastName = "";
	}

	public void set_phone(String phone_num) throws NullArgumentException
	{
		if (phone_num == null)
			throw new NullArgumentException("Null Phone");
		try {
			Long.parseLong(phone_num);
		}catch(NumberFormatException e) {
			phone_number = "";
			return;
		}
		
		if (phone_num.length() == MAX_PHONE_LEN)
			phone_number = phone_num;
		else 
			phone_number = "";
	}

	public void set_address(String addr) throws NullArgumentException
	{
		if (addr == null)
			throw new NullArgumentException("Null Address");
		if (addr.length() <= MAX_ADDR_LEN)
			street_addr = addr;
		else
			street_addr = "";
	}

	//Accessors
	public String get_ID()
	{
		return contact_ID;
	}
	
	public String get_firstName()
	{
		return firstName;
	}

	public String get_lastName()
	{
		return lastName;
	}

	public String get_phone()
	{
		return phone_number;
	}

	public String get_address()
	{
		return street_addr;
	}
}
