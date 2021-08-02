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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FeaturesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(state: FeatureState)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(state: FeatureState)

    @Query("select * from features")
    fun get(): Flow<FeatureState>

    @Query("select enabled from features where name = :name")
    fun get(name: String): Boolean
}
