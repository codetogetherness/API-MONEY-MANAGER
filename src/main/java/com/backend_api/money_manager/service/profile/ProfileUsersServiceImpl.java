package com.backend_api.money_manager.service.profile;

import com.backend_api.money_manager.dto.request.s3bucket.S3ProfileUsersRequest;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.helper.StorageUtil;
import com.backend_api.money_manager.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class ProfileUsersServiceImpl implements ProfileUserService{

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    UsersRepository usersRepository;

    private static final String UPLOAD_FOLDER_NAME = "profileUsers";



    @Autowired
    StorageUtil storageUtil;

    @Override
    public ResponseEntity<Object> updateProfileUser(S3ProfileUsersRequest s3ProfileUsersRequest) {

        String user = infoAccount.get().getEmail();

        var find = usersRepository.findByEmail(user).orElseThrow();
        System.out.println("=== find profile ===");
        System.out.println(find);
        find.setFullName(s3ProfileUsersRequest.getFileName());
        find.setPhoneNumber(s3ProfileUsersRequest.getPhoneNumber());

        File file = storageUtil.convertBase64ToFile(s3ProfileUsersRequest.getBase64(), s3ProfileUsersRequest.getFileName());

        usersRepository.save(find);

        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseHandler.generateResponseSuccess(find);
    }
}
