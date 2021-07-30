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

package com.duckduckgo.features.di

import com.duckduckgo.app.global.plugins.PluginPoint
import com.duckduckgo.di.scopes.AppObjectGraph
import com.duckduckgo.features.api.Feature
import com.duckduckgo.features.api.FeatureCustomConfigPlugin
import com.duckduckgo.features.impl.FeatureCustomConfigPluginPoint
import com.duckduckgo.features.impl.RealFeaturesImpl
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import javax.inject.Inject
import javax.inject.Singleton

@Module
@ContributesTo(AppObjectGraph::class)
abstract class FeaturesAbstractModule {

    @Multibinds
    abstract fun provideFeatureCustomConfigPlugins(): Set<@JvmSuppressWildcards FeatureCustomConfigPlugin>

}

@Module
@ContributesTo(AppObjectGraph::class)
class FeaturesModule {

    @Provides
    fun providePrivacyConfigPlugin(pluginPoint: PluginPoint<FeatureCustomConfigPlugin>): Feature {
        return RealFeaturesImpl(pluginPoint)
    }

    @Provides
    @Singleton
    fun provideFeatureCustomConfigPluginPoint(customConfigs: Set<@JvmSuppressWildcards FeatureCustomConfigPlugin>): PluginPoint<FeatureCustomConfigPlugin> {
        return FeatureCustomConfigPluginPoint(customConfigs)
    }
}
