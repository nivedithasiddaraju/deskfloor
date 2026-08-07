package com.deskfloor.repository;

import com.deskfloor.entity.Salary;
import com.deskfloor.enums.SalaryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Page<Salary> findByEmployeeId(
            Long employeeId,
            Pageable pageable
    );

    Page<Salary> findByStatus(
            SalaryStatus status,
            Pageable pageable
    );

    Page<Salary> findByEmployee_FullNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    boolean existsByEmployeeIdAndSalaryMonthAndSalaryYear(
            Long employeeId,
            String salaryMonth,
            Integer salaryYear
    );

    boolean existsByEmployeeIdAndSalaryMonthAndSalaryYearAndIdNot(
            Long employeeId,
            String salaryMonth,
            Integer salaryYear,
            Long id
    );
    long countByStatus(SalaryStatus status);

    @Query("""
SELECT COALESCE(SUM(s.netSalary),0)
FROM Salary s
WHERE s.status='PAID'
""")
    Double getTotalPayrollAmount();
    long countBySalaryMonthAndSalaryYear(
            String salaryMonth,
            Integer salaryYear
    );
    @Query("""
SELECT COALESCE(SUM(s.netSalary),0)
FROM Salary s
WHERE s.salaryMonth = :salaryMonth
AND s.salaryYear = :salaryYear
""")
    Double getMonthlyPayrollAmount(
            @org.springframework.data.repository.query.Param("salaryMonth")
            String salaryMonth,

            @org.springframework.data.repository.query.Param("salaryYear")
            Integer salaryYear
    );
    Page<Salary> findByEmployeeIdAndSalaryYear(
            Long employeeId,
            Integer salaryYear,
            Pageable pageable
    );

}