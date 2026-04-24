// Patron State: Interfaz base para todos los estados del prestamo
feat: agregar interfaz LoanState - contrato del patron State

package observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * AuditLogger — Concrete Observer
 *
 * Records every loan state transition with a timestamp.
 * Simulates a compliance audit trail that banks are legally
 * required to maintain for all credit operations.
 */
public class AuditLogger implements LoanObserver {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<String> auditLog = new ArrayList<>();

    @Override
    public void onLoanEvent(String loanId, String state, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String entry = String.format("[AUDIT] %s | Loan: %s | State: %-15s | %s",
                timestamp, loanId, state, message);
        auditLog.add(entry);
        System.out.println(entry);
    }

    /**
     * Prints the full audit trail for review or compliance export.
     */
    public void printFullAuditTrail() {
        System.out.println("\n========== FULL AUDIT TRAIL ==========");
        if (auditLog.isEmpty()) {
            System.out.println("No events recorded.");
        } else {
            auditLog.forEach(System.out::println);
        }
        System.out.println("======================================\n");
    }

    /**
     * Returns the total number of recorded events.
     * @return event count
     */
    public int getEventCount() {
        return auditLog.size();
    }
}
