package com.riverburg.eUniversity.runner;

import com.riverburg.eUniversity.model.dto.request.post.RegistrationRequest;
import com.riverburg.eUniversity.service.admin.AdminService;
import com.riverburg.eUniversity.service.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("h2")
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final AdminService adminService;

    private final StudentService studentService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setLogin("admin1");
        request.setPassword("admin1");
        request.setFullName("Adminov");
        request.setAge(20);
        request.setMail("example@gmail.com");
        adminService.register(request);
        //studentService.register(new RegistrationRequest("student2", "student2", "Emil Abbas", 10));
        //adminService.register(new RegistrationRequest("admin1", "admin1", "Administer", 40));
    }
}