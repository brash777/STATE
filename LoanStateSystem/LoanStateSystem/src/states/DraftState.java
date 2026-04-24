package states;

/**
 * DraftState — Concrete State
 *
 * The loan has been created but not yet submitted.
 * The client can still edit details or submit for review.
 *
 * Allowed transitions: DRAFT → UNDER_REVIEW
 */
public class DraftState implements LoanState {

    @Override
    public void submit(context.Loan loan) {
        System.out.println("[DRAFT] ✔ Loan application submitted successfully. Moving to UNDER_REVIEW.");
        loan.setState(new UnderReviewState());
        loan.notifyObservers("Loan submitted for credit analysis.");
    }

    @Override
    public void approve(context.Loan loan) {
        System.out.println("[DRAFT] ✘ Cannot approve a loan that has not been submitted yet.");
    }

    @Override
    public void reject(context.Loan loan) {
        System.out.println("[DRAFT] ✘ Cannot reject a loan that has not been submitted yet.");
    }

    @Override
    public void disburse(context.Loan loan) {
        System.out.println("[DRAFT] ✘ Cannot disburse funds before submission and approval.");
    }

    @Override
    public void registerPayment(context.Loan loan, double amount) {
        System.out.println("[DRAFT] ✘ No payment can be registered. The loan is still in draft.");
    }

    @Override
    public void close(context.Loan loan) {
        System.out.println("[DRAFT] ✘ Cannot close a loan that was never activated.");
    }

    @Override
    public void flagAsDefault(context.Loan loan) {
        System.out.println("[DRAFT] ✘ Cannot flag as default. Loan has not been disbursed.");
    }

    @Override
    public String getStateName() {
        return "DRAFT";
    }
}
