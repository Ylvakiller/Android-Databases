package httpServer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class BorrowedBook extends SpecificBook {

	private Date dateBorrowed;
	private String name;
	private boolean isStudent;
	public BorrowedBook(int bookID, String Author, String Title, int bookNumberID, Date date, String loanerName, boolean isStudent) {
		super(bookID, Author, Title, bookNumberID, true);
		dateBorrowed = date;
		name = loanerName;
		this.isStudent = isStudent;
	}
	public void printDetails(String username, String password){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = dateFormat.format(addDays(username, password));
		String type;
		if(isStudent){
			type = "Student";
		}else{
			type = "Teacher";
		}
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("|%-17s|%-5d|%-8s|%-30s|%-30s|%-75s", date , bookNumberID,type,  name,author,title);
		System.out.format(builder.toString()+"\n");//Moved the \n out of the stringuilder cause it wasnt being recognised
		formatter.close();
	}
	
	public Date addDays(String username, String password){
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
