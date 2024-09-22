package ucr.ac.C12599.room.jpa.entities;

public class RequestEntity {
    private String user;
    private String role;
    private String data;
    private boolean isAuthenticated;
    private boolean isCached;
    private int failedAttempts;

    public RequestEntity(String user, String role, String data) {
        this.user = user;
        this.role = role;
        this.data = data;
        this.isAuthenticated = false;
        this.isCached = false;
        this.failedAttempts = 0;
    }

    // Getters and Setters
    public String getUser() { return user; }
    public String getRole() { return role; }
    public String getData() { return data; }
    public boolean isAuthenticated() { return isAuthenticated; }
    public void setAuthenticated(boolean authenticated) { this.isAuthenticated = authenticated; }
    public boolean isCached() { return isCached; }
    public void setCached(boolean cached) { this.isCached = cached; }
    public int getFailedAttempts() { return failedAttempts; }
    public void incrementFailedAttempts() { this.failedAttempts++; }
    public void setData(String data) {
        this.data = data;
    }
}