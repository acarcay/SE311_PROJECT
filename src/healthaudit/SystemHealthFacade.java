// GROUP MEMBER 1 - [İSİM SOYİSİM]
// GROUP MEMBER 2 - [İSİM SOYİSİM]
// System Health and Audit Tool
package healthaudit;

// ============================================================================
// FACADE PATTERN
// Purpose: Provides a single, simplified entry point (runFullAudit) that
//          orchestrates the Adapter, Composite, Template Method, and Command
//          subsystems so that an administrator does not need to interact
//          with each one individually.
// ============================================================================

/**
 * Facade — hides the complexity of the four underlying subsystems behind
 * a single convenient method.
 */
public class SystemHealthFacade {

    /**
     * Runs a complete system audit in four phases:
     * <ol>
     *   <li>Adapter — select the correct OS adapter and fetch metrics.</li>
     *   <li>Composite — build the hardware tree and check every component.</li>
     *   <li>Template Method — execute the 5-step health-check algorithm.</li>
     *   <li>Command — queue and run all audit / optimisation tasks.</li>
     * </ol>
     *
     * @param osType   "Linux", "Windows", or "macOS"
     * @param isRemote true → remote server check; false → local machine check
     */
    public void runFullAudit(String osType, boolean isRemote) {

        // ── Phase 1: Adapter ─────────────────────────────────────────────
        System.out.println("═══ Phase 1: Adapter — OS Detection ═══");
        SystemAPI adapter;
        if ("Linux".equalsIgnoreCase(osType)) {
            adapter = new LinuxAdapter();
            System.out.println("  Detected OS: Linux → LinuxAdapter selected.");
        } else if ("macOS".equalsIgnoreCase(osType)) {
            adapter = new MacOSAdapter();
            System.out.println("  Detected OS: macOS → MacOSAdapter selected.");
        } else {
            adapter = new WindowsAdapter();
            System.out.println("  Detected OS: Windows → WindowsAdapter selected.");
        }

        // ── Phase 2 + 3: Composite & Template Method ─────────────────────
        // Computer (Composite root) is passed into the checker so that
        // collectData() traverses the hardware tree AND reads OS metrics
        // in one unified step — Composite and Adapter meet inside Template Method.
        System.out.println();
        System.out.println("═══ Phase 2+3: Template Method — Health Check (Composite + Adapter inside) ═══");
        Computer computer = new Computer();
        SystemChecker checker;
        if (isRemote) {
            checker = new RemoteServerChecker(adapter, computer);
        } else {
            checker = new LocalMachineChecker(adapter, computer);
        }
        checker.runCheck();

        // ── Phase 4: Command ─────────────────────────────────────────────
        System.out.println();
        System.out.println("═══ Phase 4: Command — Audit Tasks ═══");
        TaskInvoker invoker = new TaskInvoker();
        invoker.addTask(new SecurityAuditor());
        invoker.addTask(new ResourceOptimizer());
        invoker.executeAll();
    }

    // ─────────────────────────────────────────────────────────────────────
    // DEMO
    // ─────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        SystemHealthFacade facade = new SystemHealthFacade();

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   Scenario 1  ►  Linux  •  Remote Server    ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        facade.runFullAudit("Linux", true);

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   Scenario 2  ►  Windows  •  Local Machine  ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        facade.runFullAudit("Windows", false);
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   Scenario 3  ►  macOS  •  Local Machine    ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        facade.runFullAudit("macOS", false);
    }
}
