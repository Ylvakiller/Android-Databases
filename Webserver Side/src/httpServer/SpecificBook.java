package httpServer;

public class SpecificBook extends Book {
	int bookNumberID;
	public SpecificBook(int bookID, String Author, String Title, int id) {
		super(bookID, Author, Title);
		this.bookNumberID = id;
	}
	
	public void printbook(){
		System.out.print(bookNumberID +"\t");
		super.printBook();
	}

}
