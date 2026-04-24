package states;

/**
 * RejectedState — Concrete State (Terminal)
 *
 * The loan was rejected either during review or after approval revocation.
 * No further transitions are allowed from this state.
 */
public class RejectedState implements LoanState {

    @Override
    public void submit(context.Loan loan) {
        System.out.println("[REJECTED] ✘ This loan was rejected. Please create a new application.");
    }

    @Override
    public void approve(context.Loan loan) {
        System.out.println("[REJECTED] ✘ Cannot approve a rejected loan.");
    }

    @Override
    public void reject(context.Loan loan) {
        System.out.println("[REJECTED] ✘ Loan is already in rejected status.");
    }

    @Override
    public void disburse(context.Loan loan) {
        System.out.println("[REJECTED] ✘ Cannot disburse a rejected loan.");
    }

    @Override
    public void registerPayment(context.Loan loan, double amount) {
        System.out.println("[REJECTED] ✘ No payments applicable on a rejected loan.");
    }

    @Override
    public void close(context.Loan loan) {
        System.out.println("[REJECTED] ✘ Loan is already closed (rejected).");
    }

    @Override
    public void flagAsDefault(context.Loan loan) {
        System.out.println("[REJECTED] ✘ Cannot flag a rejected loan as default.");
    }

    @Override
    public String getStateName() {
        return "REJECTED";
    }
}
