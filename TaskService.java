
package net.murphyshort.TaskService;

import java.util.ArrayList;
import java.util.Iterator;

import net.murphyshort.TaskService.Task.NullArgumentException;

public class TaskService {
	
	//Constants
	public static final int NO_INDEX = -1;

	//Attributes
	private ArrayList<Task> tasks;
	private long unique_id_counter;
	
	//Constructors
	TaskService()
	{
		tasks = new ArrayList<Task>();
		unique_id_counter = 0;
	}

	//Methods
	public void add_task(Task task_in) throws NullArgumentException
	{
		task_in.set_ID(Long.toString(unique_id_counter++));
		tasks.add(task_in);
	}

	//Use an iterator to find an ID in the arrayList
	public int find_ID_index(long id)
	{
		Iterator<Task> iter = tasks.iterator();
		int index = 0;
		while (iter.hasNext()){
			if (iter.next().get_ID().equals(Long.toString(id)))
				return index;
			index++;
		}
		
		return NO_INDEX;
	}

	//Use an iterator to find the ID and remove it
	public void remove_task(long id)
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			tasks.remove(index);
	}
	
	public void remove_task(String id)
	{
		int index = find_ID_index(Long.parseLong(id));
		if (index != NO_INDEX)
			tasks.remove(index);
	}

	public void update_name(long id, String name) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			tasks.get(index).set_name(name);
	}

	public void update_description(long id, String desc) throws NullArgumentException
	{
		int index = find_ID_index(id);
		if (index != NO_INDEX)
			tasks.get(index).set_description(desc);
	}
	
	public int list_size()
	{
		return tasks.size();
	}
	
	//Return the contact, if it is within the bounds of the array, or else return a default contact
	public Task get_task_at(int index) throws NullArgumentException
	{
		Task temp;
		try {
			temp = tasks.get(index);
			return temp;
		}catch(IndexOutOfBoundsException e) {
			return new Task();
		}
	}

}
