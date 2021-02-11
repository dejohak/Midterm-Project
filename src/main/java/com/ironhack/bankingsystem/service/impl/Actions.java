package com.ironhack.bankingsystem.service.impl;

import com.ironhack.bankingsystem.enums.Status;
import com.ironhack.bankingsystem.model.AccountHolder;
import com.ironhack.bankingsystem.model.Checking;
import com.ironhack.bankingsystem.model.StudentChecking;
import com.ironhack.bankingsystem.repository.AccountHolderRepository;
import com.ironhack.bankingsystem.repository.CheckingRepository;
import com.ironhack.bankingsystem.repository.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class Actions {

    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    public void validateAgeToCreateAccount(String id) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(id);
        if (accountHolder.isPresent()) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            LocalDateTime ldt = ts.toLocalDateTime();
            int checker = ldt.getYear() - accountHolder.get().getBirthDate().getYear() - 1900;
            if (checker < 24) {
                //st checking
                long stCheckingId = (long) (Math.random() * 10000000000000000L);
                StudentChecking studentChecking =
                        new StudentChecking(stCheckingId, accountHolder.get().getName().hashCode(), Status.ACTIVE);
                studentCheckingRepository.save(studentChecking);
            } else if (checker == 24) {
                if (ldt.getMonthValue() >= accountHolder.get().getBirthDate().getMonth()+1
                        && ldt.getDayOfMonth() >= accountHolder.get().getBirthDate().getDate()) {
                    //st checking
                    long stCheckingId = (long) (Math.random() * 10000000000000000L);
                    StudentChecking studentChecking =
                            new StudentChecking(stCheckingId, accountHolder.get().getName().hashCode(), Status.ACTIVE);
                    studentCheckingRepository.save(studentChecking);
                } else {
                    // checking
                    long checkingId = (long) (Math.random() * 10000000000000000L);
                    Checking checking =
                            new Checking(checkingId, accountHolder.get().getName().hashCode(), Status.ACTIVE);
                    checkingRepository.save(checking);
                }
            } else {
                //checking
                long checkingId = (long) (Math.random() * 10000000000000000L);
                Checking checking =
                        new Checking(checkingId, accountHolder.get().getName().hashCode(), Status.ACTIVE);
                checkingRepository.save(checking);
            }
        }
    }

}
