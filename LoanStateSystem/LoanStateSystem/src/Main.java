import context.Loan;
import observer.AuditLogger;

/**
 * Main — Entry Point
 *
 * Demonstrates the State Pattern applied to a Bank Loan Management System.
 * Three scenarios are simulated:
 *
 *   Scenario A: Happy path  — Loan goes from DRAFT to CLOSED
 *   Scenario B: Rejection   — Loan is rejected after credit review
 *   Scenario C: Default     — Loan is disbursed but client fails to pay
 *
 * All actions are delegated to the current state object.
 * The Loan (context) never contains conditional logic.
 */
public class Main {

    // ══════════════════════════════════════════════════════════
    //   ENTRY POINT
    // ══════════════════════════════════════════════════════════

    public static void main(String[] args) {

        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║    BANK LOAN MANAGEMENT — STATE PATTERN DEMO      ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        scenarioA_HappyPath();
        scenarioB_Rejection();
        scenarioC_Default();
    }

    // ══════════════════════════════════════════════════════════
    //   SCENARIO A — Full successful loan lifecycle
    // ══════════════════════════════════════════════════════════

    private static void scenarioA_HappyPath() {
        printHeader("SCENARIO A: Full Successful Lifecycle (DRAFT → CLOSED)");

        AuditLogger audit = new AuditLogger();

        Loan loan = new Loan("LN-2026-001", "Maria García", 10_000.00, 0.12, 24);
        loan.addObserver(audit);
        loan.printSummary();

        // --- Invalid actions in DRAFT state ---
        System.out.println("--- Attempting invalid actions in DRAFT state ---");
        loan.approve();
        loan.disburse();
        loan.registerPayment(500);

        // --- Correct flow ---
        System.out.println("\n--- Correct lifecycle ---");
        loan.submit();
        loan.approve();
        loan.disburse();

        // Register 5 monthly payments of $500
        for (int i = 1; i <= 5; i++) {
            loan.registerPayment(500.00);
        }

        // Force close (pay remaining balance)
        loan.registerPayment(loan.getOutstandingBalance());

        // Try to act on a closed loan
        System.out.println("\n--- Attempting actions on CLOSED loan ---");
        loan.submit();
        loan.registerPayment(100);

        loan.printSummary();
        audit.printFullAuditTrail();
    }

    // ══════════════════════════════════════════════════════════
    //   SCENARIO B — Loan rejected after credit review
    // ══════════════════════════════════════════════════════════

    private static void scenarioB_Rejection() {
        printHeader("SCENARIO B: Loan Rejection (DRAFT → UNDER_REVIEW → REJECTED)");

        AuditLogger audit = new AuditLogger();

        Loan loan = new Loan("LN-2026-002", "Carlos Reyes", 50_000.00, 0.15, 36);
        loan.addObserver(audit);

        loan.submit();
        loan.reject();     // Credit analysis failed

        // Attempts to continue with rejected loan
        System.out.println("\n--- Attempting actions on REJECTED loan ---");
        loan.approve();
        loan.disburse();
        loan.registerPayment(1000);

        loan.printSummary();
        audit.printFullAuditTrail();
    }

    // ══════════════════════════════════════════════════════════
    //   SCENARIO C — Loan disbursed but defaults, then recovered
    // ══════════════════════════════════════════════════════════

    private static void scenarioC_Default() {
        printHeader("SCENARIO C: Default & Recovery (ACTIVE → DEFAULTED → CLOSED)");

        AuditLogger audit = new AuditLogger();

        Loan loan = new Loan("LN-2026-003", "Andres Mora", 25_000.00, 0.18, 48);
        loan.addObserver(audit);

        loan.submit();
        loan.approve();
        loan.disburse();

        // Client makes 3 payments, then defaults
        loan.registerPayment(600.00);
        loan.registerPayment(600.00);
        loan.registerPayment(600.00);

        System.out.println("\n--- Client stops paying. Flagging as DEFAULTED ---");
        loan.flagAsDefault();

        // Attempt normal actions on defaulted loan
        System.out.println("\n--- Attempting normal actions on DEFAULTED loan ---");
        loan.approve();
        loan.disburse();

        // Collections department recovers the debt
        System.out.println("\n--- Collections department registers recovery payments ---");
        loan.registerPayment(loan.getOutstandingBalance());   // Full recovery

        loan.printSummary();
        audit.printFullAuditTrail();
    }

    // ──────────────────────────────────────────────────────────
    // Helper
    // ──────────────────────────────────────────────────────────

    private static void printHeader(String title) {
        System.out.println("\n\n┌─────────────────────────────────────────────────────┐");
        System.out.printf( "│  %-51s│%n", title);
        System.out.println("└─────────────────────────────────────────────────────┘\n");
    }
}
