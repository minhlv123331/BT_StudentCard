package com.example.viewbinding.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.viewbinding.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ute_student_room.db"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val initialList = listOf(
                        Student(0, "Nguyễn Văn An", "20110001", "an.nv@ute.udn.vn", 3.85, "Công Nghệ Thông Tin"),
                        Student(0, "Trần Thị Mai", "20110002", "mai.tt@ute.udn.vn", 3.92, "Kỹ Thuật Phần Mềm"),
                        Student(0, "Lê Hoàng Phúc", "20110003", "phuc.lh@ute.udn.vn", 3.45, "An Toàn Thông Tin"),
                        Student(0, "Phạm Quốc Dũng", "20110004", "dung.pq@ute.udn.vn", 3.60, "Mạng Máy Tính")
                    )
                    database.studentDao().insertAll(initialList)
                }
            }
        }
    }
}