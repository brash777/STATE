# 🏦 LoanStateSystem — Patrón State
### Caso de Estudio: Sistema de Gestión de Préstamos Bancarios

---

## 📐 Estructura del Proyecto

```
LoanStateSystem/
│
├── src/
│   ├── states/
│   │   ├── LoanState.java          ← Interfaz del Patrón State
│   │   ├── DraftState.java         ← Estado: Borrador
│   │   ├── UnderReviewState.java   ← Estado: En Revisión
│   │   ├── ApprovedState.java      ← Estado: Aprobado
│   │   ├── ActiveState.java        ← Estado: Activo (con pagos)
│   │   ├── RejectedState.java      ← Estado terminal: Rechazado
│   │   ├── ClosedState.java        ← Estado terminal: Cerrado
│   │   └── DefaultedState.java     ← Estado: Incumplimiento
│   │
│   ├── context/
│   │   └── Loan.java               ← Contexto (objeto principal)
│   │
│   ├── observer/
│   │   ├── LoanObserver.java       ← Interfaz del Patrón Observer
│   │   └── AuditLogger.java        ← Registro de auditoría
│   │
│   └── Main.java                   ← Demo con 3 escenarios
│
├── frontend/
│   └── index.html                  ← UI visual (cliente, en español)
│
├── run.sh                          ← Script de compilación y ejecución
└── README.md
```

---

## 🔄 Diagrama de Estados

```
             submit()          approve()         disburse()
[DRAFT] ──────────► [UNDER_REVIEW] ──────────► [APPROVED] ──────────► [ACTIVE]
                         │                          │                      │
                    reject()                   reject()              flagAsDefault()
                         │                          │                      │
                         ▼                          ▼                      ▼
                    [REJECTED]              [REJECTED]              [DEFAULTED]
                                                                          │
                                                                   registerPayment()
                                                                     (balance=0)
                    [ACTIVE] ──── registerPayment (balance=0) ──► [CLOSED]
                    [DEFAULTED] ─ registerPayment (balance=0) ──► [CLOSED]
```

---

## 🚀 Cómo ejecutar

### Requisitos
- Java 11 o superior (`java -version`)

### Ejecutar demo
```bash
# Dar permisos al script
chmod +x run.sh

# Compilar y ejecutar
./run.sh
```

### Frontend
Abre `frontend/index.html` directamente en el navegador.

---

## 🎓 Patrones Aplicados

| Patrón   | Rol                                             |
|----------|-------------------------------------------------|
| **State**    | `LoanState` interfaz + 7 estados concretos  |
| **Observer** | `LoanObserver` + `AuditLogger` para auditoría |
| **Context**  | Clase `Loan` delega comportamiento al estado actual |

---

## 📋 Escenarios de Demo

| Escenario | Ruta                                     |
|-----------|------------------------------------------|
| **A**     | `DRAFT → UNDER_REVIEW → APPROVED → ACTIVE → CLOSED` |
| **B**     | `DRAFT → UNDER_REVIEW → REJECTED`        |
| **C**     | `ACTIVE → DEFAULTED → CLOSED` (recuperación) |

---

*Taller Patrón State — Patrones de Software — Cuarto Semestre*
