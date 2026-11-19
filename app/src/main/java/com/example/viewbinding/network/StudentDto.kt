package com.example.viewbinding.network

import com.example.viewbinding.model.Student

data class StudentDto(
    val id: Int,
    val name: String,
    val email: String
)

fun StudentDto.toEntity(): Student {
    return Student(
        id = 0,
        fullName = name,
        studentCode = "2011900$id",
        email = email,
        gpa = 3.50,
        major = "Công Nghệ Thông Tin"
    )
}