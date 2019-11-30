package mvcproject.dao;

public class FactoryDao {
    
	public static UserDao getUserDao() {
		return new UserDaoImpl();
	}
	
	public static OnlineDao getOnlineDao() {
		return new OnlineDaoImpl();
	}
}
