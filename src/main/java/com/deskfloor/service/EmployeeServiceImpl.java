package com.deskfloor.service;

import com.deskfloor.dto.EmployeeRequest;
import com.deskfloor.dto.EmployeeResponse;
import com.deskfloor.entity.Department;
import com.deskfloor.entity.Employee;
import com.deskfloor.enums.EmployeeStatus;
import com.deskfloor.exception.ResourceNotFoundException;
import com.deskfloor.repository.DepartmentRepository;
import com.deskfloor.repository.EmployeeRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.deskfloor.service.EmailService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmailService emailService;


    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            EmailService emailService) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.emailService = emailService;
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(department);
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setStatus(EmployeeStatus.ACTIVE);

        Employee savedEmployee = employeeRepository.save(employee);

        String subject = "Welcome to Deskfloor HRMS";

        String body =
                "Hello " + savedEmployee.getFullName() + ",\n\n"
                        + "Welcome to Deskfloor HRMS!\n\n"
                        + "Your employee account has been created successfully.\n\n"
                        + "Employee Code: " + savedEmployee.getEmployeeCode() + "\n"
                        + "Department: " + savedEmployee.getDepartment().getDepartmentName() + "\n"
                        + "Designation: " + savedEmployee.getDesignation() + "\n"
                        + "Joining Date: " + savedEmployee.getJoiningDate() + "\n\n"
                        + "We are happy to have you with us.\n\n"
                        + "Regards,\n"
                        + "Deskfloor HRMS";

        emailService.sendEmail(
                savedEmployee.getEmail(),
                subject,
                body
        );

        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        return mapToResponse(employee);
    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<EmployeeResponse> searchEmployees(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository
                .findByEmployeeCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartment_DepartmentNameContainingIgnoreCaseOrDesignationContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword,
                        keyword,
                        keyword,
                        pageable
                )
                .map(this::mapToResponse);
    }

    @Override
    public Page<EmployeeResponse> filterEmployees(
            String department,
            EmployeeStatus status,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository
                .findByDepartment_DepartmentNameContainingIgnoreCaseAndStatus(
                        department,
                        status,
                        pageable
                )
                .map(this::mapToResponse);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id,
                                           EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(department);
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setDateOfBirth(request.getDateOfBirth());

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        employeeRepository.delete(employee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setEmployeeCode(employee.getEmployeeCode());
        response.setFullName(employee.getFullName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());

        response.setDepartmentId(employee.getDepartment().getId());
        response.setDepartmentName(employee.getDepartment().getDepartmentName());

        response.setDesignation(employee.getDesignation());
        response.setSalary(employee.getSalary());
        response.setJoiningDate(employee.getJoiningDate());
        response.setDateOfBirth(employee.getDateOfBirth());
        response.setProfilePicture(employee.getProfilePicture());
        response.setStatus(employee.getStatus());

        return response;
    }
    @Override
    public EmployeeResponse uploadProfilePicture(
            Long employeeId,
            MultipartFile file) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Profile picture is required");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                (!contentType.equals("image/jpeg")
                        && !contentType.equals("image/png")
                        && !contentType.equals("image/jpg"))) {

            throw new RuntimeException(
                    "Only JPG, JPEG and PNG images are allowed");
        }

        try {

            Path uploadDirectory =
                    Paths.get("uploads/profile-pictures");

            Files.createDirectories(uploadDirectory);

            String originalFileName =
                    file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null &&
                    originalFileName.contains(".")) {

                extension =
                        originalFileName.substring(
                                originalFileName.lastIndexOf("."));
            }

            String fileName =
                    UUID.randomUUID() + extension;

            Path filePath =
                    uploadDirectory.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            employee.setProfilePicture(
                    filePath.toString()
            );

            Employee updatedEmployee =
                    employeeRepository.save(employee);

            return mapToResponse(updatedEmployee);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload profile picture",
                    e
            );
        }
    }
}