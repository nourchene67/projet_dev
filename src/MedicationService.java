import java.util.List;

public interface MedicationService {
    Medication getMedicationByName(String name);

    List<Medication> getMedicationsByCondition(String condition);

    List<Medication> getAlternativeMedications(String medicationName);

    boolean createMedication(Medication medication);

    boolean updateMedication(Medication medication);

    boolean deleteMedication(String name);
}