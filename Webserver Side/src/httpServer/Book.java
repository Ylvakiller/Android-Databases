package httpServer;

import java.util.Formatter;

public class Book {
	protected int ID;
	protected int amount;
	protected String author;
	protected String title;
	
	public Book(int ID, String Author, String Title, int Amount) {
		this.ID=ID;
		this.amount = Amount;
		this.author = Author;
		this.title = Title;
	}
	
	public void printBook(){
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		formatter.format("|%-4d|%-6d|%-30s|%-75s", ID,amount,author,title);
		System.out.format(builder.toString()+"\n");//Moved the \n out of the stringuilder cause it wasnt being recognised
		formatter.close();
	}

}
