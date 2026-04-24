// Patron State: Interfaz base para todos los estados del prestamo
feat: agregar interfaz LoanState - contrato del patron State

package context;

import states.LoanState;
import states.DraftState;
import observer.LoanObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Loan — Context Class (State Pattern)
 *
 * This is the central object the client interacts with.
 * It delegates all behavior to its current LoanState instance.
 * The state object itself is responsible for transitioning
 * the context to a new state when appropriate.
 *
 * Case Study: Bank Loan Management System
 *
 * Loan lifecycle:
 *   DRAFT → UNDER_REVIEW → APPROVED → ACTIVE → CLOSED
 *                       ↘ REJECTED
 *                                          ↘ DEFAULTED → CLOSED
 */
public class Loan {

    // ── Identity ──────────────────────────────────────────
    private final String loanId;
    private final String clientName;
    private final double originalAmount;

    // ── Financial ─────────────────────────────────────────
    private double outstandingBalance;
    private double interestRate;          // annual, e.g. 0.12 for 12%
    private int    termMonths;

    // ── State Pattern ─────────────────────────────────────
    private LoanState currentState;

    // ── Observer Pattern ──────────────────────────────────
    private final List<LoanObserver> observers = new ArrayList<>();

    // ─────────────────────────────────────────────────────
    // Constructor
    // ─────────────────────────────────────────────────────

    /**
     * Creates a new loan application in DRAFT state.
     *
     * @param loanId        unique identifier (e.g. "LN-2026-001")
     * @param clientName    full name of the applicant
     * @param amount        requested loan amount in USD
     * @param interestRate  annual interest rate as decimal (e.g. 0.12)
     * @param termMonths    repayment period in months
     */
    public Loan(String loanId, String clientName, double amount,
                double interestRate, int termMonths) {
        this.loanId           = loanId;
        this.clientName       = clientName;
        this.originalAmount   = amount;
        this.outstandingBalance = calculateTotalWithInterest(amount, interestRate, termMonths);
        this.interestRate     = interestRate;
        this.termMonths       = termMonths;
        this.currentState     = new DraftState();   // Every loan starts as DRAFT
    }

    // ─────────────────────────────────────────────────────
    // State Pattern — delegated actions
    // ─────────────────────────────────────────────────────

    /** Submit the loan application for credit analysis. */
    public void submit() {
        currentState.submit(this);
    }

    /** Approve the loan after passing credit analysis. */
    public void approve() {
        currentState.approve(this);
    }

    /** Reject the loan due to failed criteria. */
    public void reject() {
        currentState.reject(this);
    }

    /** Disburse funds to the client account. */
    public void disburse() {
        currentState.disburse(this);
    }

    /**
     * Register a payment installment.
     * @param amount payment amount in USD
     */
    public void registerPayment(double amount) {
        currentState.registerPayment(this, amount);
    }

    /** Close the loan (triggered when balance reaches zero). */
    public void close() {
        currentState.close(this);
    }

    /** Flag the loan as defaulted due to missed payments. */
    public void flagAsDefault() {
        currentState.flagAsDefault(this);
    }

    // ─────────────────────────────────────────────────────
    // State management (called by Concrete States only)
    // ─────────────────────────────────────────────────────

    /**
     * Transitions the loan to a new state.
     * Should only be called from within Concrete State classes.
     *
     * @param newState the target state
     */
    public void setState(LoanState newState) {
        System.out.printf("    → Transition: [%s] ──► [%s]%n",
                this.currentState.getStateName(), newState.getStateName());
        this.currentState = newState;
    }

    // ─────────────────────────────────────────────────────
    // Observer Pattern
    // ─────────────────────────────────────────────────────

    public void addObserver(LoanObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LoanObserver observer) {
        observers.remove(observer);
    }

    /** Notifies all registered observers of a loan event. */
    public void notifyObservers(String message) {
        for (LoanObserver observer : observers) {
            observer.onLoanEvent(loanId, currentState.getStateName(), message);
        }
    }

    // ─────────────────────────────────────────────────────
    // Financial helpers
    // ─────────────────────────────────────────────────────

    /**
     * Applies a payment, reducing the outstanding balance.
     * @param amount payment amount
     */
    public void applyPayment(double amount) {
        this.outstandingBalance = Math.max(0, this.outstandingBalance - amount);
    }

    /**
     * Calculates total repayment amount including simple interest.
     */
    private double calculateTotalWithInterest(double principal, double rate, int months) {
        double annualInterest = principal * rate;
        double totalInterest  = annualInterest * (months / 12.0);
        return principal + totalInterest;
    }

    // ─────────────────────────────────────────────────────
    // Getters
    // ─────────────────────────────────────────────────────

    public String  getLoanId()             { return loanId; }
    public String  getClientName()         { return clientName; }
    public double  getOriginalAmount()     { return originalAmount; }
    public double  getOutstandingBalance() { return outstandingBalance; }
    public double  getInterestRate()       { return interestRate; }
    public int     getTermMonths()         { return termMonths; }
    public String  getCurrentStateName()   { return currentState.getStateName(); }

    // ─────────────────────────────────────────────────────
    // Summary
    // ─────────────────────────────────────────────────────

    /** Prints a formatted loan summary to the console. */
    public void printSummary() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.printf( "║  LOAN SUMMARY                            ║%n");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║  Loan ID       : %-24s║%n", loanId);
        System.out.printf( "║  Client        : %-24s║%n", clientName);
        System.out.printf( "║  Original Amt  : $%-23.2f║%n", originalAmount);
        System.out.printf( "║  Outstanding   : $%-23.2f║%n", outstandingBalance);
        System.out.printf( "║  Rate          : %-23s║%n", (interestRate * 100) + "%");
        System.out.printf( "║  Term          : %-20s months║%n", termMonths);
        System.out.printf( "║  Current State : %-24s║%n", currentState.getStateName());
        System.out.println("╚══════════════════════════════════════════╝\n");
    }
}
