import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationServiceImpl implements MedicationService {
    private static final String URL = "jdbc:mysql://172.20.54.101:3306/medicationsDB";
    private static final String USER = "nourchene";
    private static final String PASSWORD = "abcd12";

    @Override
    public Medication getMedicationByName(String name) {
        String query = "SELECT * FROM medications WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Medication(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("composition"),
                        rs.getString("medication_usage"),
                        rs.getString("usage_condition"),
                        rs.getBoolean("prescription_needed")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Medication> getMedicationsByCondition(String condition) {
        String query = "SELECT * FROM medications WHERE usage_condition = ?";
        List<Medication> medications = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, condition);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                medications.add(new Medication(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("composition"),
                        rs.getString("medication_usage"),
                        rs.getString("usage_condition"),
                        rs.getBoolean("prescription_needed")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return medications;
    }

    @Override
    public List<Medication> getAlternativeMedications(String medicationName) {
        String query = "SELECT * FROM medications WHERE name != ? AND usage_condition = " +
                "(SELECT usage_condition FROM medications WHERE name = ?)";
        List<Medication> medications = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, medicationName);
            stmt.setString(2, medicationName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                medications.add(new Medication(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("composition"),
                        rs.getString("medication_usage"),
                        rs.getString("usage_condition"),
                        rs.getBoolean("prescription_needed")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return medications;
    }
    @Override
    public boolean createMedication(Medication medication) {
        String query = "INSERT INTO medications (name, composition, medication_usage, usage_condition, prescription_needed) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, medication.getName());
            stmt.setString(2, String.join(", ", medication.getComposition()));
            stmt.setString(3, String.join(", ", medication.getUsage()));
            stmt.setString(4, String.join(", ", medication.getCondition()));
            stmt.setBoolean(5, medication.isPrescriptionNeeded());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean updateMedication(Medication medication) {
        String query = "UPDATE medications SET ";

        List<String> updates = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (medication.getComposition() != null && !medication.getComposition().isEmpty()) {
            updates.add("composition = ?");
            parameters.add(medication.getComposition());
        }
        if (medication.getUsage() != null && !medication.getUsage().isEmpty()) {
            updates.add("medication_usage = ?");
            parameters.add(medication.getUsage());
        }
        if (medication.getCondition() != null && !medication.getCondition().isEmpty()) {
            updates.add("usage_condition = ?");
            parameters.add(medication.getCondition());
        }
        if (medication.isPrescriptionNeeded()) {
            updates.add("prescription_needed = ?");
            parameters.add(medication.isPrescriptionNeeded());
        }

        if (updates.isEmpty()) {
            // Nothing to update
            return false;
        }

        query += String.join(", ", updates) + " WHERE id = ?";
        parameters.add(medication.getId());

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean deleteMedication(String name) {
        String query = "DELETE FROM medications WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}