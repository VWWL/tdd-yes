package yes.tdd.framewors.domain.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalIdentityServiceResetter {
    public static void reset(IdentityService identityService) {
        GlobalIdentityService.reset(identityService);
    }
}
