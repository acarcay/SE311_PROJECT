package healthaudit;

import java.util.ArrayList;
import java.util.List;

// ============================================================================
// COMMAND PATTERN
// Purpose: Encapsulates each audit / optimisation task as a self-contained
//          object, allowing the TaskInvoker to queue, store, and execute
//          them uniformly without knowing the details of each operation.
// ============================================================================

/**
 * Command interface — every auditable task exposes a single execute() method.
 */
interface SystemTask {
    void execute();
}

// ---------------------------------------------------------------------------
// CONCRETE COMMANDS
// ---------------------------------------------------------------------------

/**
 * Concrete command — performs a security audit by scanning for open ports
 * and unpatched vulnerabilities.
 */
class SecurityAuditor implements SystemTask {

    @Override
    public void execute() {
        System.out.println("  [SecurityAuditor] Scanning for open ports...");
        System.out.println("  [SecurityAuditor] Checking for unpatched vulnerabilities...");
        System.out.println("  [SecurityAuditor] Audit complete: 2 open ports found, 1 patch pending.");
    }
}

/**
 * Concrete command — performs resource optimisation by identifying processes
 * that are hogging memory.
 */
class ResourceOptimizer implements SystemTask {

    @Override
    public void execute() {
        System.out.println("  [ResourceOptimizer] Scanning running processes...");
        System.out.println("  [ResourceOptimizer] Identifying processes hogging memory...");
        System.out.println("  [ResourceOptimizer] Optimization hint: 3 processes exceed memory threshold.");
    }
}

// ---------------------------------------------------------------------------
// INVOKER
// ---------------------------------------------------------------------------

/**
 * Invoker — maintains an ordered list of SystemTask commands and triggers
 * them all via executeAll().
 */
class TaskInvoker {
    private final List<SystemTask> tasks = new ArrayList<>();

    void addTask(SystemTask task) {
        tasks.add(task);
    }

    void executeAll() {
        System.out.println("[TaskInvoker] Executing all queued tasks...");
        for (SystemTask task : tasks) {
            task.execute();
        }
        System.out.println("[TaskInvoker] All tasks completed.");
    }
}
