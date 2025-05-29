package com.ne.domain.services;

import com.ne.domain.model.Employee;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendSalaryNotification(Employee employee, String monthYear, double amount, String institutionName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(employee.getEmail());
            helper.setSubject("Salary Credited for " + monthYear);
            helper.setText(
                    "Dear " + employee.getFirstName() + ", your salary for " + monthYear +
                            " from " + institutionName + " amounting to " + amount +
                            " has been credited to your account " + employee.getCode() + " successfully.",
                    false
            );

            mailSender.send(message);
            log.info("Email sent to {}", employee.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", employee.getEmail(), e.getMessage());
        }
    }
}
