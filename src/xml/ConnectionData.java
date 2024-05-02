package xml;

public class ConnectionData {
    private String name;
    private String weblogicJmsUrl;
    private String weblogicJndiFactoryName;
    private String connectionFactoryJndiName;
    private String user;
    private String password;

    public ConnectionData(String name, String weblogicJmsUrl, String weblogicJndiFactoryName, String connectionFactoryJndiName, String user, String password) {
        this.name = name;
        this.weblogicJmsUrl = weblogicJmsUrl;
        this.weblogicJndiFactoryName = weblogicJndiFactoryName;
        this.connectionFactoryJndiName = connectionFactoryJndiName;
        this.user = user;
        this.password = password;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getWeblogicJmsUrl() {
        return weblogicJmsUrl;
    }

    public String getWeblogicJndiFactoryName() {
        return weblogicJndiFactoryName;
    }

    public String getConnectionFactoryJndiName() {
        return connectionFactoryJndiName;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
