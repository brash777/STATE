package states;

/**
 * ApprovedState — Concrete State
 *
 * The loan has been approved by the credit officer.
 * The next step is fund disbursement to the client's account.
 *
 * Allowed transitions: APPROVED → ACTIVE
 */
public class ApprovedState implements LoanState {

    @Override
    public void submit(context.Loan loan) {
        System.out.println("[APPROVED] ✘ Loan is already approved. Submission is not applicable.");
    }

    @Override
    public void approve(context.Loan loan) {
        System.out.println("[APPROVED] ✘ Loan has already been approved.");
    }

    @Override
    public void reject(context.Loan loan) {
        System.out.println("[APPROVED] ✔ Approval revoked. Loan moved to REJECTED.");
        loan.setState(new RejectedState());
        loan.notifyObservers("Loan approval revoked and moved to rejected status.");
    }

    @Override
    public void disburse(context.Loan loan) {
        System.out.println("[APPROVED] ✔ Funds disbursed to client account. Loan is now ACTIVE.");
        loan.setState(new ActiveState());
        loan.notifyObservers("Loan funds disbursed. Repayment schedule begins.");
    }

    @Override
    public void registerPayment(context.Loan loan, double amount) {
        System.out.println("[APPROVED] ✘ Cannot register payment. Funds have not been disbursed yet.");
    }

    @Override
    public void close(context.Loan loan) {
        System.out.println("[APPROVED] ✘ Cannot close. Loan must be active and fully repaid first.");
    }

    @Override
    public void flagAsDefault(context.Loan loan) {
        System.out.println("[APPROVED] ✘ Cannot flag as default. Loan has not been disbursed yet.");
    }

    @Override
    public String getStateName() {
        return "APPROVED";
    }
}
