package com.example.viewbinding.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.viewbinding.databinding.DialogAddStudentBinding
import com.example.viewbinding.model.Student
import com.example.viewbinding.viewmodel.StudentViewModel

class AddStudentDialogFragment : DialogFragment() {

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogAddStudentBinding.inflate(LayoutInflater.from(requireContext()))

        return AlertDialog.Builder(requireContext())
            .setTitle("Thêm Sinh Viên Mới")
            .setView(binding.root)
            .setPositiveButton("Lưu vào Room") { _, _ ->
                val name = binding.etFullName.text.toString().trim()
                val code = binding.etStudentCode.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val gpa = binding.etGpa.text.toString().trim().toDoubleOrNull() ?: 0.0

                if (name.isEmpty() || code.isEmpty()) {
                    Toast.makeText(requireContext(), "Họ tên và MSSV không được để trống!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val student = Student(
                    fullName = name,
                    studentCode = code,
                    email = if (email.isNotEmpty()) email else "${code.lowercase()}@ute.udn.vn",
                    gpa = gpa
                )
                viewModel.insert(student)
            }
            .setNegativeButton("Hủy", null)
            .create()
    }
}