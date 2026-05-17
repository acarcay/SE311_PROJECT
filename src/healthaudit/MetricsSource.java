package healthaudit;

// ============================================================================
// ADAPTER PATTERN
// Purpose: Converts the incompatible interfaces of OS-specific metric APIs
//          (Linux, Windows, and macOS) into the unified SystemAPI interface
//          that the rest of the application expects.
// ============================================================================

/**
 * Target interface — the uniform contract that all metric consumers depend on.
 * Provides OS-agnostic access to system data, memory usage, and process usage.
 */
interface SystemAPI {
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
// ---------------------------------------------------------------------------
// MACOS ADAPTEE + ADAPTER
// ---------------------------------------------------------------------------

/**
 * Adaptee — simulates the native macOS Darwin/XNU kernel calls that return
 * raw, platform-specific data structures.
 */
class MacOSAPI {
    String sysctlbynameKernOsproductversion() {
        System.out.println("  [MacOSAPI] Calling sysctlbyname(\"kern.osproductversion\")...");
        return "macOS 14.4.1 Sonoma (Darwin 23.4.0) arm64";
    }

    String hostStatistics64() {
        System.out.println("  [MacOSAPI] Calling host_statistics64(HOST_VM_INFO64)...");
        return "PhysicalMemory: 16384 MB | WiredMemory: 3072 MB | ActiveMemory: 8192 MB | FreeMemory: 5120 MB";
    }

    String getfsstat() {
        System.out.println("  [MacOSAPI] Calling getfsstat() on /Volumes/Macintosh HD...");
        return "TotalBlocks: 976562500 | FreeBlocks: 244140625 | BlockSize: 4096";
    }
}

/**
 * Adapter — wraps MacOSAPI and translates its raw outputs into the
 * normalized SystemAPI contract.
 */
class MacOSAdapter implements SystemAPI {
    private final MacOSAPI macOSAPI = new MacOSAPI();

    @Override
    public String getSystemData() {
        String raw = macOSAPI.sysctlbynameKernOsproductversion();
        return "System: " + raw;
    }

    @Override
    public String getMemoryUsage() {
        String raw = macOSAPI.hostStatistics64();
        return "Memory: " + raw;
    }

    @Override
    public String getProcessUsage() {
        String raw = macOSAPI.getfsstat();
        return "Disk/Process: " + raw;
    }
}
