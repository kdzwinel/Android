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

package com.duckduckgo.privacy.config.di

import com.duckduckgo.di.scopes.AppObjectGraph
import com.duckduckgo.privacy.config.api.PrivacyConfigDownloader
import com.duckduckgo.privacy.config.impl.RealPrivacyConfigDownloader
import com.duckduckgo.privacy.config.impl.Test
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Binds
import dagger.Module
import dagger.Provides

//@PrivacyConfigScope
//@MergeSubcomponent(
//    scope = AppObjectGraph::class
//)
//interface TestComponentInterface
//
//@Module
//@ContributesTo(AppObjectGraph::class)
//class TestModule {
//
//    @Provides
//    fun provideTest(): Test = Test()
//}
