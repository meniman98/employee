package com.cts.employee.repo;

import com.cts.employee.model.Employee;
import com.cts.employee.model.EmployeePage;
import com.cts.employee.model.EmployeeSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeCriteriaRepo {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployeeCriteriaRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Employee> findAllWithFilters(EmployeePage employeePage, EmployeeSearchCriteria searchCriteria) {
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate predicate = getPredicate(searchCriteria, employeeRoot);
        criteriaQuery.where(predicate);
        setOrder(employeePage, criteriaQuery, employeeRoot);

        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
        typedQuery.setMaxResults(employeePage.getPageSize());

        Pageable pageable = getPageable(employeePage);
        long employeesCount = getEmployeesCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(EmployeeSearchCriteria searchCriteria, Root<Employee> employeeRoot) {
        List<Predicate> predicateList = new ArrayList<>();

        if (Objects.nonNull(searchCriteria.getName())) {
            predicateList.add(criteriaBuilder.like(
                    employeeRoot.get("name"),
                    "%" + searchCriteria.getName() + "%")
            );
        }

        if (Objects.nonNull(searchCriteria.getDepartment())) {
            predicateList.add(criteriaBuilder.like(
                    employeeRoot.get("department"),
                    "%" + searchCriteria.getDepartment() + "%")
            );
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private long getEmployeesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(EmployeePage employeePage) {
        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
    }

    private void setOrder(EmployeePage employeePage,
                          CriteriaQuery<Employee> query,
                          Root<Employee> employeeRoot) {

        if (employeePage.getSortDirection().isAscending()) {
            query.orderBy(criteriaBuilder.asc(employeeRoot.get(employeePage.getSortBy())));
        }
        else {
            query.orderBy(criteriaBuilder.desc(employeeRoot.get(employeePage.getSortBy())));
        }
    }
}
