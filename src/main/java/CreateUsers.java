import org.apache.commons.lang3.RandomStringUtils;

public class CreateUsers {
    private String email;
    private String password;
    private String name;

    public CreateUsers() {
    }

    public CreateUsers(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    //создать случайного пользователя
    public static CreateUsers getUser() {
        return new CreateUsers(
                RandomStringUtils.randomAlphanumeric(8) + "@yandex.ru",
                "0123456",
                "UserName" + RandomStringUtils.randomAlphanumeric(7)
        );
    }
}
