package healthaudit;

// ============================================================================
// TEMPLATE METHOD PATTERN
// Purpose: Defines the invariant 5-step skeleton of a system health check
//          (setup → collectData → checkData → analyze → generateReport),
//          while letting subclasses override individual steps — most notably
//          setup() — to tailor behaviour for local or remote targets.
// ============================================================================

/**
 * Abstract class that locks the health-check algorithm into exactly five
 * sequential steps.  Subclasses MUST provide their own setup(); the
 * remaining four steps carry sensible default implementations.
 */
abstract class SystemChecker {

    /**
     * Template method — final so that no subclass can alter the order
     * or number of steps in the health-check algorithm.
     */
    public final void runCheck() {
        setup();
        collectData();
        checkData();
        analyze();
        generateReport();
    }

    /** Step 1: Prepare the environment / connection. (abstract — must override) */
    protected abstract void setup();

    /** Step 2: Gather raw metrics from the target machine. */
    protected void collectData() {
        System.out.println("  [Step 2] Collecting system metrics...");
    }

    /** Step 3: Validate collected data for completeness and integrity. */
    protected void checkData() {
        System.out.println("  [Step 3] Validating collected data...");
    }

    /** Step 4: Analyse metrics against predefined thresholds. */
    protected void analyze() {
        System.out.println("  [Step 4] Analyzing results against thresholds...");
    }

    /** Step 5: Compile and output the final health report. */
    protected void generateReport() {
        System.out.println("  [Step 5] Generating health report... Done.");
    }
}

// ---------------------------------------------------------------------------
// CONCRETE IMPLEMENTATIONS
// ---------------------------------------------------------------------------

/**
 * Concrete checker — performs the health check on the local machine by
 * directly accessing OS-level resources.
 */
class LocalMachineChecker extends SystemChecker {

    @Override
    protected void setup() {
        System.out.println("  [Step 1] Setting up local machine check...");
        System.out.println("           Acquiring local resource handles.");
    }
}

/**
 * Concrete checker — performs the health check on a remote server by
 * establishing a socket connection before proceeding.
 */
class RemoteServerChecker extends SystemChecker {

    @Override
    protected void setup() {
        System.out.println("  [Step 1] Setting up remote server check...");
        System.out.println("           Opening socket connection to 192.168.1.100:8080...");
        System.out.println("           Connection established.");
    }
}
