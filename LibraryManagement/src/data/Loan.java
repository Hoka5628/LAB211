package data;

import java.io.Serializable;
import java.util.Date;

public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionID;
    private String bookID;
    private String userID;
    private Date borrowDate;
    private Date returnDate;

    public Loan(String transactionID, String bookID, String userID, Date borrowDate, Date returnDate) {
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.userID = userID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return String.format("|%-15s|%-15s|%-15s|%-30s|%-30s|%n", transactionID, bookID, userID,
                borrowDate != null ? borrowDate.toString() : "N/A",
                returnDate != null ? returnDate.toString() : "N/A");
    }
}