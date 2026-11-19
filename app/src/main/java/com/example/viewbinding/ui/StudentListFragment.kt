package com.example.viewbinding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewbinding.R
import com.example.viewbinding.databinding.FragmentStudentListBinding
import com.example.viewbinding.model.Student
import com.example.viewbinding.ui.adapter.StudentAdapter
import com.example.viewbinding.viewmodel.StudentViewModel

class StudentListFragment : Fragment() {

    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(
            onStudentClick = { student -> showStudentDetailDialog(student) },
            onDeleteClick = { student -> confirmDelete(student) }
        )
        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@StudentListFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewModel.allStudents.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.layoutEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            binding.tvStudentCountBadge.text = "${list.size} sinh viên"
        }

        viewModel.statusMessage.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            AddStudentDialogFragment().show(childFragmentManager, "ADD_STUDENT_DIALOG")
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_sync -> {
                    viewModel.syncFromCloud()
                    true
                }
                else -> false
            }
        }
    }

    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(requireContext())
            .setTitle("Xác Nhận Xóa")
            .setMessage("Bạn có chắc muốn xóa sinh viên ${student.fullName} khỏi CSDL Room?")
            .setPositiveButton("Xóa") { _, _ -> viewModel.delete(student) }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showStudentDetailDialog(student: Student) {
        AlertDialog.Builder(requireContext())
            .setTitle("Thông Tin Sinh Viên")
            .setMessage("ID: ${student.id}\nHọ Tên: ${student.fullName}\nMSSV: ${student.studentCode}\nEmail: ${student.email}\nĐiểm GPA: ${student.gpa}")
            .setPositiveButton("Đóng", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}