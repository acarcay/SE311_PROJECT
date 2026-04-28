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
     * @param osType   "Linux" or "Windows"
     * @param isRemote true → remote server check; false → local machine check
     */
    public void runFullAudit(String osType, boolean isRemote) {

        // ── Phase 1: Adapter ─────────────────────────────────────────────
        System.out.println("═══ Phase 1: Adapter — Fetching OS Metrics ═══");
        SystemAPI adapter;
        if ("Linux".equalsIgnoreCase(osType)) {
            adapter = new LinuxAdapter();
        } else {
            adapter = new WindowsAdapter();
        }
        System.out.println(adapter.getSystemData());
        System.out.println(adapter.getMemoryUsage());
        System.out.println(adapter.getProcessUsage());

        // ── Phase 2: Composite ───────────────────────────────────────────
        System.out.println();
        System.out.println("═══ Phase 2: Composite — Hardware Tree Check ═══");
        Computer computer = new Computer();
        computer.checkStatus();

        // ── Phase 3: Template Method ─────────────────────────────────────
        System.out.println();
        System.out.println("═══ Phase 3: Template Method — Health Check ═══");
        SystemChecker checker;
        if (isRemote) {
            checker = new RemoteServerChecker();
        } else {
            checker = new LocalMachineChecker();
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
    }
}
