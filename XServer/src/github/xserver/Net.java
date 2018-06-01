package github.xserver;

public class Net {

	// Requests from client to server
	public static String LOGIN			= "LOGIN";
	public static String ISGAMEREADY	= "ISGAMEREADY";
	public static String MYPIECE		= "MYPIECE";
	public static String ISMYTURN		= "ISMYTURN";
	public static String GETBOARD		= "GETBOARD";
	public static String MOVE			= "MOVE";
	public static String PLAY			= "PLAY";
	public static String MYSTATS		= "MYSTATS";
	public static String CONCEDE		= "CONCEDE";
	public static String REGISTER		= "REGISTER";

	// Responses from server to client
	public static String LOGINACCEPTED	= "LOGINACCEPTED";
	public static String LOGINDENIED	= "LOGINDENIED";
	public static String REGISTERSUCCESS= "REGISTERSUCCESS";
	public static String REGISTERFAIL	= "REGISTERFAIL";
	public static String NO				= "NO";
	public static String YES			= "YES";
	public static String X				= "X";
	public static String O				= "O";
	public static String YOUWIN			= "YOUWIN";
	public static String YOULOSE		= "YOULOSE";
	public static String ENEMYTURN		= "ENEMYTURN";
	
}


