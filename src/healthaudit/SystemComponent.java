package healthaudit;

import java.util.ArrayList;
import java.util.List;

// ============================================================================
// COMPOSITE PATTERN
// Purpose: Represents the physical hardware hierarchy as a tree structure,
//          allowing uniform treatment of individual devices (leaves) and
//          device groups (composites) through a single HardwareComponent
//          interface.
// ============================================================================

/**
 * Component interface — the common contract for every node in the
 * hardware tree, whether it is a single device or a composite group.
 */
interface HardwareComponent {
    void checkStatus();
}

// ---------------------------------------------------------------------------
// LEAF NODES
// ---------------------------------------------------------------------------

/**
 * Leaf — represents a single CPU device.
 */
class CPU implements HardwareComponent {
    @Override
    public void checkStatus() {
        System.out.println("    [CPU] Status: Running at 3.5 GHz, temperature 62°C.");
    }
}

/**
 * Leaf — represents a single Memory module.
 */
class Memory implements HardwareComponent {
    @Override
    public void checkStatus() {
        System.out.println("    [Memory] Status: 12288 MB used of 16384 MB total.");
    }
}

/**
 * Leaf — represents a single Network Interface Card.
 */
class NIC implements HardwareComponent {
    @Override
    public void checkStatus() {
        System.out.println("    [NIC] Status: Link up, 1 Gbps, 0 errors.");
    }
}

// ---------------------------------------------------------------------------
// COMPOSITE NODES
// ---------------------------------------------------------------------------

/**
 * Composite — represents the ISA Bus that contains peripheral devices.
 * In this hierarchy it holds the NIC.
 */
class ISABus implements HardwareComponent {
    private final List<HardwareComponent> children = new ArrayList<>();

    ISABus() {
        children.add(new NIC());
    }

    @Override
    public void checkStatus() {
        System.out.println("  [ISABus] Scanning bus devices...");
        for (HardwareComponent child : children) {
            child.checkStatus();
        }
    }
}

/**
 * Composite — represents the Motherboard that hosts core components.
 * Contains: CPU, Memory, and ISABus.
 */
class Motherboard implements HardwareComponent {
    private final List<HardwareComponent> children = new ArrayList<>();

    Motherboard() {
        children.add(new CPU());
        children.add(new Memory());
        children.add(new ISABus());
    }

    @Override
    public void checkStatus() {
        System.out.println("  [Motherboard] Checking onboard components...");
        for (HardwareComponent child : children) {
            child.checkStatus();
        }
    }
}

/**
 * Composite — represents the entire Computer, the root of the hardware tree.
 * Contains: Motherboard (and by extension all nested components).
 */
class Computer implements HardwareComponent {
    private final List<HardwareComponent> children = new ArrayList<>();

    Computer() {
        children.add(new Motherboard());
    }

    @Override
    public void checkStatus() {
        System.out.println("[Computer] Running full hardware check...");
        for (HardwareComponent child : children) {
            child.checkStatus();
        }
        System.out.println("[Computer] Hardware check complete.");
    }
}
