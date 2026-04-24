// Patron State: Interfaz base para todos los estados del prestamo
feat: agregar interfaz LoanState - contrato del patron State

package states;

/**
 * ActiveState — Concrete State
 *
 * Funds have been disbursed. The loan is active and the client
 * is expected to make monthly installment payments.
 * If the outstanding balance reaches zero, the loan is closed.
 * If payments are missed, the loan can be flagged as defaulted.
 *
 * Allowed transitions: ACTIVE → CLOSED | DEFAULTED
 */
public class ActiveState implements LoanState {

    @Override
    public void submit(context.Loan loan) {
        System.out.println("[ACTIVE] ✘ Loan is already active. Cannot re-submit.");
    }

    @Override
    public void approve(context.Loan loan) {
        System.out.println("[ACTIVE] ✘ Loan is already approved and active.");
    }

    @Override
    public void reject(context.Loan loan) {
        System.out.println("[ACTIVE] ✘ Cannot reject an active loan. Flag as default or close instead.");
    }

    @Override
    public void disburse(context.Loan loan) {
        System.out.println("[ACTIVE] ✘ Funds already disbursed. Loan is currently active.");
    }

    @Override
    public void registerPayment(context.Loan loan, double amount) {
        if (amount <= 0) {
            System.out.println("[ACTIVE] ✘ Payment amount must be greater than zero.");
            return;
        }

        double previousBalance = loan.getOutstandingBalance();
        loan.applyPayment(amount);
        double newBalance = loan.getOutstandingBalance();

        System.out.printf("[ACTIVE] ✔ Payment of $%.2f registered. Previous balance: $%.2f | New balance: $%.2f%n",
                amount, previousBalance, newBalance);

        loan.notifyObservers(String.format("Payment of $%.2f received. Outstanding balance: $%.2f", amount, newBalance));

        if (newBalance <= 0) {
            System.out.println("[ACTIVE] ✔ Outstanding balance is zero. Automatically closing the loan.");
            close(loan);
        }
    }

    @Override
    public void close(context.Loan loan) {
        if (loan.getOutstandingBalance() > 0) {
            System.out.printf("[ACTIVE] ✘ Cannot close loan. Outstanding balance remaining: $%.2f%n",
                    loan.getOutstandingBalance());
            return;
        }
        System.out.println("[ACTIVE] ✔ Loan fully repaid. Marking as CLOSED.");
        loan.setState(new ClosedState());
        loan.notifyObservers("Loan fully repaid and closed successfully.");
    }

    @Override
    public void flagAsDefault(context.Loan loan) {
        System.out.println("[ACTIVE] ✔ Loan flagged as DEFAULTED due to missed payments.");
        loan.setState(new DefaultedState());
        loan.notifyObservers("Loan flagged as defaulted. Collection process initiated.");
    }

    @Override
    public String getStateName() {
        return "ACTIVE";
    }
}
