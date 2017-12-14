package httpServer;

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
		System.out.println("ID\t" + ID + "\tAmount\t" + amount + "\tAuthor\t" +  author + "\tTitle\t" + title);
	}

}
