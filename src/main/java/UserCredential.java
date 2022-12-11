
public class UserCredential {

    private String email;
    private String password;
    private String name;

    public UserCredential(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    //использовать учетные данные пользователя
    public static UserCredential from(CreateUsers user) {
        return new UserCredential(user.getEmail(), user.getPassword(), user.getName());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
