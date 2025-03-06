package com.backend_api.money_manager.service.users;

import com.backend_api.money_manager.dto.request.users.AccountVerification;
import com.backend_api.money_manager.entity.AccountStatus;
import com.backend_api.money_manager.entity.BalanceUser;
import com.backend_api.money_manager.entity.LogVerification;
import com.backend_api.money_manager.entity.Users;
import com.backend_api.money_manager.exception.ResponseHandler;
import com.backend_api.money_manager.helper.GenerateOtp;
import com.backend_api.money_manager.helper.InfoAccount;
import com.backend_api.money_manager.helper.MailService;
import com.backend_api.money_manager.repository.BalanceUserRepository;
import com.backend_api.money_manager.repository.LogVerificationRepository;
import com.backend_api.money_manager.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    InfoAccount infoAccount;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LogVerificationRepository logVerificationRepository;

    @Autowired
    MailService mailService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    BalanceUserRepository balanceUserRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> verificationAccount(AccountVerification request) {
        try{
            String user = infoAccount.get().getEmail(); //Untuk menemukan email

            LogVerification verify = logVerificationRepository.getByUserAndExp(user,request.getCode()); //mendapatkan email dan code

            if(verify == null){ //jika email dan code kosong maka, kembalikan error
                return ResponseHandler.generateResponseError(HttpStatus.BAD_REQUEST,"FAILED","Otp yang anda masukan salah");
            }

            if(verify.getExpired() != null) {
                LocalDateTime getExpi = verify.getExpired();
                LocalDateTime currentTime = LocalDateTime.now();

                if(isExpired(getExpi, currentTime)) {
                    LogVerification verification = new LogVerification();
                    verification.setCode(GenerateOtp.generateRandomNumber());
                    verification.setExpired(GenerateOtp.getExpiryDate());
                    var saveLog = logVerificationRepository.save(verification);
                    return ResponseHandler.generateResponseSuccess(saveLog);

                } else {
                    System.out.println("Masih berlaku dek");
                }
            }

            verify.setIsVerify(true);
            logVerificationRepository.save(verify);

            Users data = usersRepository.findByEmail(user).orElseThrow();
            data.setAccountStatus(AccountStatus.VERIFIED);
            var save = usersRepository.save(data);

            Context context = new Context();

            context.setVariable("name", save.getFullName());
            String emailContent = templateEngine.process("email_success_verification", context);
            mailService.sendEmail(data.getEmail(),"Success Verification", emailContent);

            BalanceUser balance = new BalanceUser();

            balance.setId(UUID.randomUUID().toString());
            balance.setValue("0");
            balance.setUser(save);

            balanceUserRepository.save(balance);
            return ResponseHandler.generateResponseSuccess(save);

        }catch (Exception e){
            return ResponseHandler.generateResponseError(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"");
        }

    }


    public ResponseEntity<Object> returnOneTimePassword() {
        LogVerification logVerification = new LogVerification();

        logVerification.setCode(GenerateOtp.generateRandomNumber());
        logVerification.setExpired(GenerateOtp.getExpiryDate());

        var save = logVerificationRepository.save(logVerification);

        return ResponseHandler.generateResponseSuccess(save);

    }

    public static boolean isExpired(LocalDateTime expiredTime, LocalDateTime currentTime) {
        return Duration.between(expiredTime,currentTime).toMinutes() >= 5;
    }

}