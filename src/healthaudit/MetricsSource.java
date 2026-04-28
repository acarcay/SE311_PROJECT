package healthaudit;

// ============================================================================
// ADAPTER PATTERN
// Purpose: Converts the incompatible interfaces of OS-specific metric APIs
//          (Linux and Windows) into the unified SystemAPI interface that
//          the rest of the application expects.
// ============================================================================

/**
 * Target interface — the uniform contract that all metric consumers depend on.
 * Provides OS-agnostic access to system data, memory usage, and process usage.
 */
public interface SystemAPI {
    String getSystemData();
    String getMemoryUsage();
    String getProcessUsage();
}

// ---------------------------------------------------------------------------
// LINUX ADAPTEE + ADAPTER
// ---------------------------------------------------------------------------

/**
 * Adaptee — simulates the native Linux C-library / kernel calls that return
 * raw, platform-specific data structures.
 */
class LinuxAPI {
    String uname() {
        System.out.println("  [LinuxAPI] Calling uname()...");
        return "Linux 6.5.0-generic x86_64";
    }

    String fopenProcMeminfo() {
        System.out.println("  [LinuxAPI] Reading /proc/meminfo via fopen()...");
        return "MemTotal: 16384 MB | MemAvailable: 4096 MB";
    }

    String statvfs() {
        System.out.println("  [LinuxAPI] Calling statvfs(\"/\")...");
        return "Blocks: 1048576 | Available: 262144 | BlockSize: 4096";
    }
}

/**
 * Adapter — wraps LinuxAPI and translates its raw outputs into the
 * normalized SystemAPI contract.
 */
class LinuxAdapter implements SystemAPI {
    private final LinuxAPI linuxAPI = new LinuxAPI();

    @Override
    public String getSystemData() {
        String raw = linuxAPI.uname();
        return "System: " + raw;
    }

    @Override
    public String getMemoryUsage() {
        String raw = linuxAPI.fopenProcMeminfo();
        return "Memory: " + raw;
    }

    @Override
    public String getProcessUsage() {
        String raw = linuxAPI.statvfs();
        return "Disk/Process: " + raw;
    }
}

// ---------------------------------------------------------------------------
// WINDOWS ADAPTEE + ADAPTER
// ---------------------------------------------------------------------------

/**
 * Adaptee — simulates the native Windows API (kernel32 / Win32) calls that
 * return raw, platform-specific data structures.
 */
class WindowsAPI {
    String GetSystemInfo() {
        System.out.println("  [WindowsAPI] Calling GetSystemInfo()...");
        return "Windows 11 Build 22631 x64";
    }

    String GlobalMemoryStatusEx() {
        System.out.println("  [WindowsAPI] Calling GlobalMemoryStatusEx()...");
        return "TotalPhys: 16384 MB | AvailPhys: 5120 MB";
    }

    String GetDiskFreeSpace() {
        System.out.println("  [WindowsAPI] Calling GetDiskFreeSpace()...");
        return "TotalClusters: 524288 | FreeClusters: 131072 | SectorSize: 512";
    }
}

/**
 * Adapter — wraps WindowsAPI and translates its raw outputs into the
 * normalized SystemAPI contract.
 */
class WindowsAdapter implements SystemAPI {
    private final WindowsAPI windowsAPI = new WindowsAPI();

    @Override
    public String getSystemData() {
        String raw = windowsAPI.GetSystemInfo();
        return "System: " + raw;
    }

    @Override
    public String getMemoryUsage() {
        String raw = windowsAPI.GlobalMemoryStatusEx();
        return "Memory: " + raw;
    }

    @Override
    public String getProcessUsage() {
        String raw = windowsAPI.GetDiskFreeSpace();
        return "Disk/Process: " + raw;
    }
}
