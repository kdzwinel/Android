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
import com.duckduckgo.features.api.FeatureCustomConfigPlugin
import com.duckduckgo.privacy.config.api.PrivacyConfigDownloader
import com.duckduckgo.privacy.config.plugins.PrivacyConfigPlugin
import com.duckduckgo.privacy.config.impl.RealPrivacyConfigDownloader
import com.duckduckgo.privacy.config.network.PrivacyConfigService
import com.duckduckgo.privacy.config.plugins.PrivacyFeaturePlugin
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@ContributesTo(AppObjectGraph::class)
class PrivacyConfigModule {

    @Provides
    fun providePrivacyConfigDownloader(privacyConfigService: PrivacyConfigService, privacyFeaturePluginPoint: PluginPoint<PrivacyFeaturePlugin>): PrivacyConfigDownloader {
        return RealPrivacyConfigDownloader(privacyConfigService, privacyFeaturePluginPoint)
    }

    @Provides
    @Singleton
    @IntoSet
    fun providePrivacyConfigPlugin(privacyConfigDownloader: PrivacyConfigDownloader): FeatureCustomConfigPlugin {
        return PrivacyConfigPlugin(privacyConfigDownloader)
    }

}
