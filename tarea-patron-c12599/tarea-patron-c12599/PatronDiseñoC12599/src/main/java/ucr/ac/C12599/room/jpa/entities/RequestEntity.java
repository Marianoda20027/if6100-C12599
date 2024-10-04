package ucr.ac.C12599.room.jpa.entities;

public class RequestEntity {
    private String user;
    private String role;
    private String data;
    private boolean authenticated;
    private boolean cached;
    private boolean authorized;
    private int failedAttempts;

    // Constructor
    public RequestEntity(String user, String role, String data) {
        this.user = user;
        this.role = role;
        this.data = data;
        this.authenticated = false;
        this.cached = false;
        this.authorized = true;
        this.failedAttempts = 0;  // Inicializar en 0
    }

    // Getters y Setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }
}
