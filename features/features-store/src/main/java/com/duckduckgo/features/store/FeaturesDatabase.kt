/*
 * Copyright (c) 2021 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.features.store

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    exportSchema = true, version = 1,
    entities = [
        FeatureState::class
    ]
)
abstract class FeaturesDatabase : RoomDatabase() {

    abstract fun featuresDao(): FeaturesDao

    companion object {
        @Volatile
        private var INSTANCE: FeaturesDatabase? = null

        fun getInstance(context: Context): FeaturesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): FeaturesDatabase {
            return Room.databaseBuilder(context, FeaturesDatabase::class.java, "features.db")
                .enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
