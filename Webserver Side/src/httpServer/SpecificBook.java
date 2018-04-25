package httpServer;

public class SpecificBook extends Book {
	public int bookNumberID;
	public boolean borrowed;
	public SpecificBook(int bookID, String Author, String Title, int id) {
		super(bookID, Author, Title);
		this.bookNumberID = id;
		this.borrowed=false;
	}
	
	public SpecificBook(int bookID, String Author, String Title, int id, boolean isBorrowed) {
		super(bookID, Author, Title);
		this.bookNumberID = id;
		this.borrowed = isBorrowed;
	}
	
	public void printbook(){
		System.out.print("|"+bookNumberID +"\t|"+borrowed+"\t");
		super.printBook();
	}
	

}
