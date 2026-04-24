package observer;

/**
 * LoanObserver Interface — Observer Pattern
 *
 * Any class that wants to be notified about loan state changes
 * must implement this interface. Used for audit logging,
 * notifications, and compliance reporting.
 */
public interface LoanObserver {

    /**
     * Called whenever a loan state transition occurs.
     *
     * @param loanId  the unique identifier of the loan
     * @param state   the new state name after the transition
     * @param message a human-readable description of the event
     */
    void onLoanEvent(String loanId, String state, String message);
}
