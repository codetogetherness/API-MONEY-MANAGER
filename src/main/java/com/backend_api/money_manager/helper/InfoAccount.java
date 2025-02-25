package com.backend_api.money_manager.helper;

import com.backend_api.money_manager.entity.Users;
import com.backend_api.money_manager.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class InfoAccount {

    @Autowired
    UsersRepository usersRepository;


    public Users get(){

        var auth =  SecurityContextHolder.getContext().getAuthentication();
        var name = auth.getName();
        var data = usersRepository.findByEmail(name);

        if(data.isEmpty()){
            return null;
        }

        return data.get();

    }

//    public Users getExpired() {
//
//        var auth = SecurityContextHolder.getContext().getAuthentication();
//        var name = auth.g;
//        var data = expRepository.getByExpired(name);
//    }

}
