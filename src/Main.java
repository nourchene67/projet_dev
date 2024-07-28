import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to MediAssist");
        MedicationService medicationService = new MedicationServiceImpl();
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please log in:");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user;
        try {
            user = authService.authenticate(username, password);
            if (user == null) {
                System.out.println("Invalid username or password. Exiting.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Get Medication by Name");
            System.out.println("2. Get Medications by Condition");
            System.out.println("3. Get Alternative Medications");

            if (authService.isDoctor(user)) {
                System.out.println("4. Create Medication");
                System.out.println("5. Update Medication");
                System.out.println("6. Delete Medication");
                System.out.println("7. Exit");
            } else {
                System.out.println("4. Exit");
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter medication name:");
                    String name = scanner.nextLine();
                    Medication medication = medicationService.getMedicationByName(name);
                    if (medication != null) {
                        System.out.println("Medication Found: " + medication.getDescription());
                    } else {
                        System.out.println("Medication not found.");
                    }
                    break;

                case 2:
                    System.out.println("Enter condition:");
                    String condition = scanner.nextLine();
                    List<Medication> medicationsByCondition = medicationService.getMedicationsByCondition(condition);
                    if (!medicationsByCondition.isEmpty()) {
                        System.out.println("Medications for condition " + condition + ":");
                        for (Medication med : medicationsByCondition) {
                            System.out.println(med.getDescription());
                        }
                    } else {
                        System.out.println("No medications found for condition " + condition);
                    }
                    break;

                case 3:
                    System.out.println("Enter medication name for alternatives:");
                    String medicationName = scanner.nextLine();
                    List<Medication> alternativeMedications = medicationService.getAlternativeMedications(medicationName);
                    if (!alternativeMedications.isEmpty()) {
                        System.out.println("Alternative Medications for " + medicationName + ":");
                        for (Medication med : alternativeMedications) {
                            System.out.println(med.getDescription());
                        }
                    } else {
                        System.out.println("No alternative medications found for " + medicationName);
                    }
                    break;

                case 4:
                    if (authService.isDoctor(user)) {
                        System.out.println("Enter medication details (name, composition, usage, condition, prescription_needed):");
                        String newName = scanner.nextLine();
                        String newComposition = scanner.nextLine();
                        String newUsage = scanner.nextLine();
                        String newCondition = scanner.nextLine();
                        boolean newPrescriptionNeeded = scanner.nextBoolean();
                        scanner.nextLine(); // Consume newline
                        Medication newMedication = new Medication(null, newName, newComposition, newUsage, newCondition, newPrescriptionNeeded);
                        boolean created = medicationService.createMedication(newMedication);
                        if (created) {
                            System.out.println("Medication created successfully.");
                        } else {
                            System.out.println("Failed to create medication.");
                        }
                        break;
                    } else {
                        System.out.println("Exiting. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }

                case 5:
                    if (authService.isDoctor(user)) {
                        System.out.println("Enter medication ID to update:");
                        String updateId = scanner.next();
                        scanner.nextLine(); // Consume newline

                        System.out.println("Enter new composition (leave blank to keep current):");
                        String updateComposition = scanner.nextLine();
                        updateComposition = updateComposition.isEmpty() ? null : updateComposition;

                        System.out.println("Enter new usage (leave blank to keep current):");
                        String updateUsage = scanner.nextLine();
                        updateUsage = updateUsage.isEmpty() ? null : updateUsage;

                        System.out.println("Enter new condition (leave blank to keep current):");
                        String updateCondition = scanner.nextLine();
                        updateCondition = updateCondition.isEmpty() ? null : updateCondition;

                        System.out.println("Enter new prescription needed status (true/false, leave blank to keep current):");
                        String updatePrescriptionNeededInput = scanner.nextLine();
                        Boolean updatePrescriptionNeeded = updatePrescriptionNeededInput.isEmpty() ? null : Boolean.parseBoolean(updatePrescriptionNeededInput);

                        Medication updateMedication = new Medication(updateId, null, updateComposition, updateUsage, updateCondition, updatePrescriptionNeeded);
                        boolean updated = medicationService.updateMedication(updateMedication);
                        if (updated) {
                            System.out.println("Medication updated successfully.");
                        } else {
                            System.out.println("Failed to update medication.");
                        }
                        break;
                    }

                case 6:
                    if (authService.isDoctor(user)) {
                        System.out.println("Enter medication name to delete:");
                        String deleteName = scanner.nextLine();
                        boolean deleted = medicationService.deleteMedication(deleteName);
                        if (deleted) {
                            System.out.println("Medication deleted successfully.");
                        } else {
                            System.out.println("Failed to delete medication.");
                        }
                        break;
                    }

                case 7:
                    if (authService.isDoctor(user)) {
                        System.out.println("Exiting. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}