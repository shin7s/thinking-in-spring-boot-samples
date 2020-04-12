package thinking.in.spring.boot.samples.spring4.jdbc;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thinking.in.spring.boot.samples.spring4.domain.User;

@Transactional(propagation = Propagation.REQUIRED)
public interface UserService {
    public void save(User user) throws Exception;
}
