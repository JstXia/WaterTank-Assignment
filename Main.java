package school;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// ===== ABSTRACT CLASS =====
abstract class WaterTank {
    protected float level;
    protected final float capacity;

    public WaterTank(float capacity) {
        this.capacity = capacity;
        this.level = 0;
    }

    public void addWater(float liters) {
        level += liters;
    }

    public void removeWater(float liters) {
        level -= liters;
    }

    public float getCurrentLevel() {
        return level;
    }

    public float getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return level >= capacity;
    }

    public boolean isEmpty() {
        return level <= 0;
    }

    public String getStatus() {
        if (isFull()) return "Full";
        if (isEmpty()) return "Empty";
        return "In Use";
    }
}

// ===== HOMETANK =====
class HomeTank extends WaterTank {
    public HomeTank() {
        super(200f);
    }
}

// ===== BUILDINGTANK =====
class BuildingTank extends WaterTank {
    public BuildingTank() {
        super(2000f);
    }
}

// ===== MAIN GUI PROGRAM =====
public class Main {
    private JFrame frame;
    private JPanel panel;
    private JLabel titleLabel, capacityLabel, levelLabel, statusLabel;
    private JButton addBtn, removeBtn, statusBtn, exitBtn;
    private WaterTank tank;

    public Main(WaterTank tank) {
        this.tank = tank;
        createUI();
    }

    private void createUI() {
        // Create JFrame
        frame = new JFrame("Water Tank System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel with null layout
        panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        // Title label
        titleLabel = new JLabel("WATER TANK CONTROL SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(80, 20, 340, 30);
        panel.add(titleLabel);

        // Capacity label
        capacityLabel = new JLabel("Tank Capacity: " + tank.getCapacity() + " liters");
        capacityLabel.setBounds(140, 70, 300, 20);
        panel.add(capacityLabel);

        // Current level label
        levelLabel = new JLabel("Current Level: " + tank.getCurrentLevel() + " liters");
        levelLabel.setBounds(140, 100, 300, 20);
        panel.add(levelLabel);

        // Status label
        statusLabel = new JLabel("Status: " + tank.getStatus());
        statusLabel.setBounds(140, 130, 300, 20);
        panel.add(statusLabel);

        // Add Water button
        addBtn = new JButton("Add Water");
        addBtn.setBounds(60, 180, 150, 40);
        panel.add(addBtn);

        // Remove Water button
        removeBtn = new JButton("Remove Water");
        removeBtn.setBounds(260, 180, 150, 40);
        panel.add(removeBtn);

        // Check Status button
        statusBtn = new JButton("Check Status");
        statusBtn.setBounds(60, 250, 150, 40);
        panel.add(statusBtn);

        // Exit button
        exitBtn = new JButton("Exit");
        exitBtn.setBounds(260, 250, 150, 40);
        panel.add(exitBtn);

        // Button Listeners
        addBtn.addActionListener(e -> addWater());
        removeBtn.addActionListener(e -> removeWater());
        statusBtn.addActionListener(e -> showStatus());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private void addWater() {
        if (tank.isFull()) {
            JOptionPane.showMessageDialog(frame, "Tank is already FULL!");
            return;
        }
        String input = JOptionPane.showInputDialog(frame, "Enter liters to add:");
        if (input == null) return;
        try {
            float liters = Float.parseFloat(input);
            float availableSpace = tank.getCapacity() - tank.getCurrentLevel();
            if (liters > availableSpace) {
                JOptionPane.showMessageDialog(frame, "Cannot add! Only " + availableSpace + " liters can be added.");
            } else {
                tank.addWater(liters);
                updateLabels();
                JOptionPane.showMessageDialog(frame, liters + " liters added successfully!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
        }
    }

    private void removeWater() {
        if (tank.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Tank is EMPTY!");
            return;
        }
        String input = JOptionPane.showInputDialog(frame, "Enter liters to remove:");
        if (input == null) return;
        try {
            float liters = Float.parseFloat(input);
            if (liters > tank.getCurrentLevel()) {
                JOptionPane.showMessageDialog(frame, "Not enough water! You only have " + tank.getCurrentLevel() + " liters.");
            } else {
                tank.removeWater(liters);
                updateLabels();
                JOptionPane.showMessageDialog(frame, liters + " liters removed successfully!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
        }
    }

    private void showStatus() {
        JOptionPane.showMessageDialog(frame,
                "Tank Status: " + tank.getStatus() + "\n" +
                        "Current Level: " + tank.getCurrentLevel() + " / " + tank.getCapacity() + " liters");
    }

    private void updateLabels() {
        levelLabel.setText("Current Level: " + tank.getCurrentLevel() + " liters");
        statusLabel.setText("Status: " + tank.getStatus());
    }

    public static void main(String[] args) {
        // Ask user which tank to use
        String[] options = {"Home Tank (200L)", "Building Tank (2000L)"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose the type of tank:",
                "Tank Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        WaterTank selectedTank;
        if (choice == 0) {
            selectedTank = new HomeTank();
        } else if (choice == 1) {
            selectedTank = new BuildingTank();
        } else {
            JOptionPane.showMessageDialog(null, "Program exited.");
            return;
        }

        new Main(selectedTank);
    }
}
