package states;

/**
 * LoanState Interface — State Pattern
 *
 * Defines all actions that can be attempted on a loan
 * regardless of its current state. Each concrete state
 * decides what happens when an action is triggered.
 *
 * Design Pattern: State (Behavioral)
 * Author: Software Patterns Workshop
 */
public interface LoanState {

    /**
     * Submit the loan application for initial review.
     * @param loan the Loan context object
     */
    void submit(context.Loan loan);

    /**
     * Approve the loan after credit analysis.
     * @param loan the Loan context object
     */
    void approve(context.Loan loan);

    /**
     * Reject the loan due to failed criteria.
     * @param loan the Loan context object
     */
    void reject(context.Loan loan);

    /**
     * Disburse the approved loan funds to the client.
     * @param loan the Loan context object
     */
    void disburse(context.Loan loan);

    /**
     * Register a payment made by the client.
     * @param loan   the Loan context object
     * @param amount the payment amount in USD
     */
    void registerPayment(context.Loan loan, double amount);

    /**
     * Mark the loan as fully paid off.
     * @param loan the Loan context object
     */
    void close(context.Loan loan);

    /**
     * Flag the loan as defaulted due to missed payments.
     * @param loan the Loan context object
     */
    void flagAsDefault(context.Loan loan);

    /**
     * Returns the human-readable name of this state.
     * @return state name string
     */
    String getStateName();
}
