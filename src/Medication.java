public class Medication extends DatabaseItem {
    private String name;
    private String composition;
    private String usage;
    private String condition;
    private boolean prescriptionNeeded;

    public Medication(String id, String name, String composition, String usage, String condition, boolean prescriptionNeeded) {
        super(id);
        this.name = name;
        this.composition = composition;
        this.usage = usage;
        this.condition = condition;
        this.prescriptionNeeded = prescriptionNeeded;
    }

    // Implement the abstract method
    @Override
    public String getDescription() {
        return "Medication: " + name + ", Composition: " + composition + ", Usage: " + usage + ", Condition: " + condition + ", Prescription Needed: " + prescriptionNeeded;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isPrescriptionNeeded() {
        return prescriptionNeeded;
    }

    public void setPrescriptionNeeded(boolean prescriptionNeeded) {
        this.prescriptionNeeded = prescriptionNeeded;
    }

}
