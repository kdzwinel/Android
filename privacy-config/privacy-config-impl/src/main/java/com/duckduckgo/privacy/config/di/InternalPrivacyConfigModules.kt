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

import com.duckduckgo.app.global.plugins.PluginPoint
import com.duckduckgo.di.scopes.AppObjectGraph
import com.duckduckgo.privacy.config.network.PrivacyConfigService
import com.duckduckgo.privacy.config.plugins.PrivacyFeaturePlugin
import com.duckduckgo.privacy.config.plugins.PrivacyFeaturePluginPoint
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@ContributesTo(AppObjectGraph::class)
abstract class PrivacyFeaturesBindingModule {

    @Multibinds
    abstract fun providePrivacyFeatureStorePlugins(): Set<@JvmSuppressWildcards PrivacyFeaturePlugin>

}

@Module
@ContributesTo(AppObjectGraph::class)
class NetworkModule {
    @Provides
    fun providePrivacyConfigService(@Named("api") retrofit: Retrofit): PrivacyConfigService {
        return retrofit.create(PrivacyConfigService::class.java)
    }

    @Provides
    @Singleton
    fun providePrivacyFeaturePluginPoint(customConfigs: Set<@JvmSuppressWildcards PrivacyFeaturePlugin>): PluginPoint<PrivacyFeaturePlugin> {
        return PrivacyFeaturePluginPoint(customConfigs)
    }
}
