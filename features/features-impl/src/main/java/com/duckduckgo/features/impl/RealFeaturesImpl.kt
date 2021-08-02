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

package com.duckduckgo.features.impl

import com.duckduckgo.app.global.plugins.PluginPoint
import com.duckduckgo.features.api.Feature
import com.duckduckgo.features.api.FeatureCustomConfigPlugin
import com.duckduckgo.features.api.FeatureName
import com.duckduckgo.features.store.FeatureState
import com.duckduckgo.features.store.FeaturesDatabase
import timber.log.Timber

class RealFeaturesImpl(featuresDatabase: FeaturesDatabase, private val featureCustomConfigPluginPoint: PluginPoint<FeatureCustomConfigPlugin>) : Feature {

    private val featuresDao = featuresDatabase.featuresDao()

    override fun isEnabled(featureName: FeatureName, defaultValue: Boolean) {
        featuresDao.get(featureName.value)
    }

    override fun downloadConfigs() {
        Timber.d("Download configs")
        Timber.d("FeatureCustomConfigPluginPoint size is ${featureCustomConfigPluginPoint.getPlugins().size}")
        featureCustomConfigPluginPoint.getPlugins().forEach { plugin ->
            val features = plugin.download()
            features.forEach { feature ->
                val featureState = FeatureState(feature.first.value, feature.second)
                featuresDao.insert(featureState)
            }
        }
    }
}

class FeatureCustomConfigPluginPoint(
    private val customConfigs: Set<@JvmSuppressWildcards FeatureCustomConfigPlugin>
) : PluginPoint<FeatureCustomConfigPlugin> {
    override fun getPlugins(): List<FeatureCustomConfigPlugin> {
        return customConfigs.toList()
    }
}
