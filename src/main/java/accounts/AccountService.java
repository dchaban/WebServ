package accounts;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class AccountService {
    private Session session;

    public AccountService(Session session) {
        this.session = session;
    }

    public long addUser(UserProfile userProfile) {
        return (Long) session.save(userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

}
