package httpServer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class BorrowedBook extends SpecificBook {

	private Date dateBorrowed;
	private String name;
	private boolean isStudent;
	public BorrowedBook(int bookID, String Author, String Title, int id, Date date, String loanerName, boolean isStudent) {
		super(bookID, Author, Title, id, true);
		dateBorrowed = date;
		name = loanerName;
		this.isStudent = isStudent;
	}
	public void printDetails(String username, String password){
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("|%-10d|%-30s|%-30s|%-75s", addDays(username, password).toString(), name,author,title);
		System.out.format(builder.toString()+"\n");//Moved the \n out of the stringuilder cause it wasnt being recognised
		formatter.close();
	}
	
	private Date addDays(String username, String password){
		int amount = 0;
		if(isStudent){
			amount = Communication.getSetting(username, password, "StudentBookDayLimit");
		}else{
			amount = Communication.getSetting(username, password, "TeacherBookDayLimit");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(dateBorrowed);
		c.add(Calendar.DATE, amount);  // number of days to add
		return c.getTime();  // dt is now the new date
	}

}
