package com.ne.domain.services;

import com.ne.domain.enums.PayslipStatus;
import com.ne.domain.model.Message;
import com.ne.domain.model.Payslip;
import com.ne.domain.repositories.MessageRepository;
import com.ne.domain.repositories.PayslipRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class PayrollApprovalService {

    private final PayslipRepository payslipRepository;
    private final MessageRepository messageRepository;
    private final EmailService emailService;

    @Transactional
    public void approvePayroll(int month, int year, String institutionName) {
        List<Payslip> payslips = payslipRepository.findByMonthAndYear(month, year);

        for (Payslip payslip : payslips) {
            if (payslip.getStatus() == PayslipStatus.PENDING) {
                payslip.setStatus(PayslipStatus.PAID);
                payslipRepository.save(payslip); // update status

                // Create and save a message
                String monthName = java.time.Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                String monthYear = monthName + " " + year;

                String content = "Dear " + payslip.getEmployee().getFirstName() +
                        ", your salary for " + month + "/" + year +
                        " from " + institutionName + " amounting to " + payslip.getNetSalary() +
                        " has been credited to your account " + payslip.getEmployee().getCode() + " successfully.";

                Message message = Message.builder()
                        .employee(payslip.getEmployee())
                        .content(content)
                        .monthYear(month + "/" + year)
                        .sentDate(LocalDateTime.now())
                        .build();

                messageRepository.save(message);

                // Send Email
                emailService.sendSalaryNotification(payslip.getEmployee(), month + "/" + year,
                        payslip.getNetSalary(), institutionName);
            }
        }
    }
}
