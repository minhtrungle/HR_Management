package Service;

import Dao.UserDAO;
import Model.User;

public class AuthenService {
    private UserDAO userDAO = new UserDAO();
    public boolean login(String userName, String passWord) {
        User user = userDAO.getByUserNameAndPassword(userName, passWord);
        if(user == null){
            return false;
        }
        //Đăng nhập thành công
        return true;
    }
}
