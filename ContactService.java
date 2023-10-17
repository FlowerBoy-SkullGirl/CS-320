package net.murphyshort.ContactTests;

import java.util.*; //ArrayList and iterator

import net.murphyshort.ContactTests.Contact.NullArgumentException;

/*
 * A class that manages in-memory a list of Contacts, assigns to them ID's,
 * and may modify their attributes
 */
public class ContactService
{
	//Constants
	public static final int NO_INDEX = -1;

	//Attributes
	private ArrayList<Contact> contacts;
	private long unique_id_counter;
	
	//Constructors
	ContactService()
	{
		contacts = new ArrayList<Contact>();
		unique_id_counter = 0;
	}

	//Methods
	public void add_contact(Contact cont) throws NullArgumentException
	{
		cont.set_ID(Long.toString(unique_id_counter++));
		contacts.add(cont);
	}

	//Use an iterator to find an ID in the arrayList
	public int find_ID_index(long id)
	{
		Iterator<Contact> iter = contacts.iterator();
		int index = 0;
		while (iter.hasNext()){
			if (iter.next().get_ID().equals(Long.toString(id)))
				return index;
			index++;
		}
		
		return NO_INDEX;
	}

	//Use an iterator to find the ID and remove it
	public void remove_contact(long id)
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			contacts.remove(index);
	}
	
	public void remove_contact(String id)
	{
		int index = find_ID_index(Long.parseLong(id));
		if (index != NO_INDEX)
			contacts.remove(index);
	}

	public void update_firstName(long id, String name) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			contacts.get(index).set_firstName(name);
	}

	public void update_lastName(long id, String name) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			contacts.get(index).set_lastName(name);
	}

	public void update_phone(long id, String phone) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			contacts.get(index).set_phone(phone);
	}

	public void update_address(long id, String addr) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			contacts.get(index).set_address(addr);
	}
	
	public int list_size()
	{
		return contacts.size();
	}
	
	//Return the contact, if it is within the bounds of the array, or else return a default contact
	public Contact get_contact_at(int index) throws NullArgumentException
	{
		Contact temp;
		try {
			temp = contacts.get(index);
			return temp;
		}catch(IndexOutOfBoundsException e) {
			return new Contact();
		}
	}

}

