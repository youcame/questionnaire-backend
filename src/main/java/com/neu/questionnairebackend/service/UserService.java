package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.questionnairebackend.model.dto.ModifyUserRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author HUANG
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-29 21:33:13
*/
public interface UserService extends IService<User> {
    /**
     *
     * @param userAccount
     * @param password
     * @param checkPassword
     * @return 用户id
     */
    long userRegister(String userAccount, String password, String checkPassword);

    /**
     * @param userAccount
     * @param password
     * @param request
     * @return 用户信息
     */
    User userLogin(String userAccount, String password, HttpServletRequest request);

    /**
     *
     * @param user
     * @return
     */
    User getSafeUser(User user);

    /**
     * @param request
     */
    int userLogout(HttpServletRequest request);

    /**
     *
     * @param user
     * @return
     */
    boolean updateFrontUser(ModifyUserRequest user);

//    /**
//     *
//     * @param id
//     * @return 改写删除，把删除掉的id抹除掉
//     */
//    @Override
//    public boolean removeById(Serializable id);
}
