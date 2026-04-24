package states;

/**
 * UnderReviewState — Concrete State
 *
 * The loan is currently being analyzed by the credit team.
 * A credit officer can approve or reject it based on risk criteria.
 *
 * Allowed transitions: UNDER_REVIEW → APPROVED | REJECTED
 */
public class UnderReviewState implements LoanState {

    @Override
    public void submit(context.Loan loan) {
        System.out.println("[UNDER_REVIEW] ✘ Loan is already submitted and currently under review.");
    }

    @Override
    public void approve(context.Loan loan) {
        System.out.println("[UNDER_REVIEW] ✔ Credit analysis passed. Loan approved. Moving to APPROVED.");
        loan.setState(new ApprovedState());
        loan.notifyObservers("Loan approved by credit officer.");
    }

    @Override
    public void reject(context.Loan loan) {
        System.out.println("[UNDER_REVIEW] ✔ Credit analysis failed. Loan rejected. Moving to REJECTED.");
        loan.setState(new RejectedState());
        loan.notifyObservers("Loan rejected after credit analysis.");
    }

    @Override
    public void disburse(context.Loan loan) {
        System.out.println("[UNDER_REVIEW] ✘ Cannot disburse. The loan must be approved first.");
    }

    @Override
    public void registerPayment(context.Loan loan, double amount) {
        System.out.println("[UNDER_REVIEW] ✘ Cannot register payments. Loan has not been disbursed.");
    }

    @Override
    public void close(context.Loan loan) {
        System.out.println("[UNDER_REVIEW] ✘ Cannot close a loan that is still under review.");
    }

    @Override
    public void flagAsDefault(context.Loan loan) {
        System.out.println("[UNDER_REVIEW] ✘ Cannot flag as default. Loan has not been disbursed.");
    }

    @Override
    public String getStateName() {
        return "UNDER_REVIEW";
    }
}
