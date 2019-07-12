/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.wsgwz.basemodule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.wsgwz.basemodule.widgets.suspension.bean.SuspensionWindowBean
import cn.wsgwz.basemodule.widgets.suspension.bean.SuspensionWindowDao

/**
 * The Room database for this app
 */
@Database(entities = [ User::class, SuspensionWindowBean::class], version = 3, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class BaseAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun suspensionWindowDao(): SuspensionWindowDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: BaseAppDatabase? = null

        fun getInstance(context: Context): BaseAppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): BaseAppDatabase {
            return Room.databaseBuilder(context, BaseAppDatabase::class.java, "base_module.db")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            /*val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance().enqueue(request)*/
                        }
                    })
                    .build()
        }
    }
}
