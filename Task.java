package net.murphyshort.TaskService;

public class Task {
	
	public class NullArgumentException extends Exception {
		private static final long serialVersionUID = 1L;

		public NullArgumentException(String errorMessage){
			super(errorMessage);
		}
	}
	//Constants
	//Public so that they may be used in tests without maintaining separate constants in the Test class
	public final int MAX_ID_LEN = 10;
	public final int MAX_NAME_LEN = 20;
	public final int MAX_DESC_LEN = 50;

	//Attributes
	private String task_ID;
	private String name;
	private String description;
	
	//Constructors
	//Requirements state that the fields are required, but not that they should contain characters:
	//Requirement requires validation
	Task() throws NullArgumentException
	{
		set_ID("");
		set_name("");
		set_description("");
	}

	//Mutators
	public void set_ID(String id) throws NullArgumentException
	{
		if (id == null)
			throw new NullArgumentException("Null ID");
		//return if an ID is already set
		if (task_ID != "" && task_ID != null)
			return;
		if (id.length() <= MAX_ID_LEN)
			task_ID = id;
		else
			task_ID = "";
	}

	public void set_name(String name_in) throws NullArgumentException
	{
		if (name_in == null)
			throw new NullArgumentException("Null Name");
		if (name_in.length() <= MAX_NAME_LEN)
			name = name_in;
		else
			name = "";
	}

	public void set_description(String desc) throws NullArgumentException
	{
		if (desc == null)
			throw new NullArgumentException("Null Description");
		if (desc.length() <= MAX_DESC_LEN)
			description = desc;
		else
			description = "";
	}

	//Accessors
	public String get_ID()
	{
		return task_ID;
	}
	
	public String get_name()
	{
		return name;
	}

	public String get_description()
	{
		return description;
	}
}
