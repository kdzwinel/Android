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

package com.duckduckgo.privacy.config.plugins

import com.duckduckgo.app.global.plugins.PluginPoint
import com.duckduckgo.features.api.FeatureName
import org.json.JSONObject

interface PrivacyFeaturePlugin {
    fun store(featureName: FeatureName, jsonObject: JSONObject?): Boolean
    val featureName: FeatureName
}

class PrivacyFeaturePluginPoint(
    private val privacyFeatures: Set<@JvmSuppressWildcards PrivacyFeaturePlugin>
) : PluginPoint<PrivacyFeaturePlugin> {
    override fun getPlugins(): List<PrivacyFeaturePlugin> {
        return privacyFeatures.toList()
    }
}
