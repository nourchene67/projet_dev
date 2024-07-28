public abstract class DatabaseItem {
    private String id;

    public DatabaseItem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getDescription();
}