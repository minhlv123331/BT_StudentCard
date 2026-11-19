package com.example.viewbinding.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "student_code")
    val studentCode: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "gpa")
    val gpa: Double,

    @ColumnInfo(name = "major")
    val major: String = "Công Nghệ Thông Tin"
)