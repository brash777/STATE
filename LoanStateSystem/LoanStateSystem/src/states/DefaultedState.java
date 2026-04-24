package states;

/**
 * DefaultedState — Concrete State
 *
 * The client failed to make required payments.
 * The bank's collections department takes over.
 * A partial recovery payment can still be registered,
 * and if fully resolved, the loan can be closed.
 *
 * Allowed transitions: DEFAULTED → CLOSED (if recovered)
 */
public class DefaultedState implements LoanState {

    @Override
    public void submit(context.Loan loan) {
        System.out.println("[DEFAULTED] ✘ Cannot submit. Loan is in default status.");
    }

    @Override
    public void approve(context.Loan loan) {
        System.out.println("[DEFAULTED] ✘ Cannot approve. Loan is in default status.");
    }

    @Override
    public void reject(context.Loan loan) {
        System.out.println("[DEFAULTED] ✘ Cannot reject. Loan is in default status.");
    }

    @Override
    public void disburse(context.Loan loan) {
        System.out.println("[DEFAULTED] ✘ Cannot disburse. Loan is in default status.");
    }

    @Override
    public void registerPayment(context.Loan loan, double amount) {
        if (amount <= 0) {
            System.out.println("[DEFAULTED] ✘ Recovery payment must be greater than zero.");
            return;
        }

        double previousBalance = loan.getOutstandingBalance();
        loan.applyPayment(amount);
        double newBalance = loan.getOutstandingBalance();

        System.out.printf("[DEFAULTED] ✔ Recovery payment of $%.2f registered. Remaining debt: $%.2f%n",
                amount, newBalance);

        loan.notifyObservers(String.format("Recovery payment of $%.2f received. Remaining: $%.2f", amount, newBalance));

        if (newBalance <= 0) {
            System.out.println("[DEFAULTED] ✔ Full debt recovered. Loan will be CLOSED.");
            close(loan);
        }
    }

    @Override
    public void close(context.Loan loan) {
        if (loan.getOutstandingBalance() > 0) {
            System.out.printf("[DEFAULTED] ✘ Cannot close. Remaining debt: $%.2f%n",
                    loan.getOutstandingBalance());
            return;
        }
        System.out.println("[DEFAULTED] ✔ Debt fully recovered. Loan marked as CLOSED.");
        loan.setState(new ClosedState());
        loan.notifyObservers("Defaulted loan debt recovered and closed.");
    }

    @Override
    public void flagAsDefault(context.Loan loan) {
        System.out.println("[DEFAULTED] ✘ Loan is already in default status.");
    }

    @Override
    public String getStateName() {
        return "DEFAULTED";
    }
}
