package com.example.viewbinding.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.viewbinding.R
import com.example.viewbinding.data.AppDatabase
import com.example.viewbinding.repository.StudentRepository
import com.example.viewbinding.viewmodel.StudentViewModel
import com.example.viewbinding.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val repository by lazy { StudentRepository(database.studentDao()) }
    private val viewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Kích hoạt khởi tạo ViewModel qua Factory trước khi Fragment dùng chung
        viewModel.allStudents

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, StudentListFragment())
                .commit()
        }
    }
}