package healthaudit;

import java.util.ArrayList;
import java.util.List;

// ============================================================================
// COMMAND PATTERN
// Purpose: Encapsulates each audit / optimisation task as a self-contained
//          object, allowing the TaskInvoker to queue, store, and execute
//          them uniformly without knowing the details of each operation.
//          Each command receives a SystemAPI adapter and a HardwareComponent
//          root so that it can query real OS metrics and hardware state —
//          directly connecting Command to both Adapter and Composite patterns.
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
 * Concrete command — performs a security audit by checking NIC hardware
 * status (Composite) and querying OS system data (Adapter) to simulate
 * detection of open ports and unpatched vulnerabilities.
 */
class SecurityAuditor implements SystemTask {

    private final SystemAPI         metricsSource;
    private final HardwareComponent hardwareRoot;

    SecurityAuditor(SystemAPI metricsSource, HardwareComponent hardwareRoot) {
        this.metricsSource = metricsSource;
        this.hardwareRoot  = hardwareRoot;
    }

    @Override
    public void execute() {
        System.out.println("  [SecurityAuditor] Reading OS system info for vulnerability check...");
        System.out.println("  [SecurityAuditor]   " + metricsSource.getSystemData());
        System.out.println("  [SecurityAuditor] Inspecting network hardware state...");
        hardwareRoot.checkStatus();
        System.out.println("  [SecurityAuditor] Audit complete: 2 open ports found, 1 patch pending.");
    }
}

/**
 * Concrete command — performs resource optimisation by reading memory
 * metrics (Adapter) and traversing the hardware tree (Composite) to
 * identify components and processes that exceed memory thresholds.
 */
class ResourceOptimizer implements SystemTask {

    private final SystemAPI         metricsSource;
    private final HardwareComponent hardwareRoot;

    ResourceOptimizer(SystemAPI metricsSource, HardwareComponent hardwareRoot) {
        this.metricsSource = metricsSource;
        this.hardwareRoot  = hardwareRoot;
    }

    @Override
    public void execute() {
        System.out.println("  [ResourceOptimizer] Reading memory metrics from OS adapter...");
        System.out.println("  [ResourceOptimizer]   " + metricsSource.getMemoryUsage());
        System.out.println("  [ResourceOptimizer] Checking hardware memory module status...");
        hardwareRoot.checkStatus();
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
