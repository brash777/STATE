package states;

/**
 * ClosedState — Concrete State (Terminal)
 *
 * The loan has been fully repaid. All actions are blocked.
 * This is a successful terminal state.
 */
public class ClosedState implements LoanState {

    @Override
    public void submit(context.Loan loan) {
        System.out.println("[CLOSED] ✘ Loan is closed. No further actions permitted.");
    }

    @Override
    public void approve(context.Loan loan) {
        System.out.println("[CLOSED] ✘ Loan is closed. No further actions permitted.");
    }

    @Override
    public void reject(context.Loan loan) {
        System.out.println("[CLOSED] ✘ Loan is closed. No further actions permitted.");
    }

    @Override
    public void disburse(context.Loan loan) {
        System.out.println("[CLOSED] ✘ Loan is closed. No further actions permitted.");
    }

    @Override
    public void registerPayment(context.Loan loan, double amount) {
        System.out.println("[CLOSED] ✘ Loan is already paid in full. No payment needed.");
    }

    @Override
    public void close(context.Loan loan) {
        System.out.println("[CLOSED] ✘ Loan is already closed.");
    }

    @Override
    public void flagAsDefault(context.Loan loan) {
        System.out.println("[CLOSED] ✘ Cannot flag a closed loan as default.");
    }

    @Override
    public String getStateName() {
        return "CLOSED";
    }
}
