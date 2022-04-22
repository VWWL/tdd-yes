package yes.tdd.users.domain;

import yes.tdd.frameworks.domain.core.GlobalIdentityService;

public final class User {
    private final UserId id;
    private final UserInfo userInfo;

    public static User newInstance(String name, String email) {
        return of(GlobalIdentityService.next(), name, email);
    }

    public static User of(String id, String name, String email) {
        return new User(id, name, email);
    }

    private User(String id, String name, String email) {
        this.id = new UserId(id);
        this.userInfo = new UserInfo(name, email);
    }

    public String id() {
        return id.id();
    }

    public String name() {
        return userInfo.name();
    }

    public String email() {
        return userInfo.email();
    }
}
