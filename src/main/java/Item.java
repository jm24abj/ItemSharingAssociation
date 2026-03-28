

/**
 *
 * @author br191
 */
public class Item {
    private String title;
    private String language;
    private Member donatedBy;
    private Member onLoanTo;

    public Item(String title, String language, Member donatedBy) {
        this.title = title;
        this.language = language;
        // donatedBy is String in constructor but Member field - convert/lookup as needed
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void loanTo(Member borrower) {
        borrower.lend(this);
        this.onLoanTo = borrower;
    }
    
    public Member getLoanMember() {
        return onLoanTo;
    }

    public boolean isAvailable() {
        return onLoanTo == null;
    }

    public void returnLoan() {
        this.onLoanTo = null;
    }

    public Member getDonator() {
        return donatedBy;
    }

    public void clearDonator() {
        this.donatedBy = null;
    }
}


 
