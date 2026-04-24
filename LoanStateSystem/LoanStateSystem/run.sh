#!/bin/bash
# ═══════════════════════════════════════════════════════════
#   LoanStateSystem — Compile & Run Script
#   State Pattern: Bank Loan Management Case Study
# ═══════════════════════════════════════════════════════════

set -e

SRC="./src"
OUT="./out"

echo ""
echo "╔═══════════════════════════════════════════════════╗"
echo "║   LoanStateSystem — State Pattern in Java         ║"
echo "╚═══════════════════════════════════════════════════╝"
echo ""

# ── Step 1: Clean previous build ────────────────────────
echo "🧹  Limpiando compilación anterior..."
rm -rf "$OUT"
mkdir -p "$OUT"

# ── Step 2: Compile ──────────────────────────────────────
echo "⚙️   Compilando fuentes Java..."

javac -d "$OUT" \
  "$SRC/states/LoanState.java" \
  "$SRC/states/DraftState.java" \
  "$SRC/states/UnderReviewState.java" \
  "$SRC/states/ApprovedState.java" \
  "$SRC/states/ActiveState.java" \
  "$SRC/states/RejectedState.java" \
  "$SRC/states/ClosedState.java" \
  "$SRC/states/DefaultedState.java" \
  "$SRC/observer/LoanObserver.java" \
  "$SRC/observer/AuditLogger.java" \
  "$SRC/context/Loan.java" \
  "$SRC/Main.java"

echo "✅  Compilación exitosa."
echo ""

# ── Step 3: Run ──────────────────────────────────────────
echo "🚀  Ejecutando demo del Patrón State..."
echo "────────────────────────────────────────"
echo ""

java -cp "$OUT" Main

echo ""
echo "────────────────────────────────────────"
echo "✅  Demo completado."
echo ""
echo "💡  Para el frontend, abre:"
echo "    frontend/index.html  en tu navegador"
echo ""
