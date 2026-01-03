package com.example.demo.repository

import com.example.demo.model.Department
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository {
    fun findById(departmentId: String): Department?
    fun save(department: Department)
}