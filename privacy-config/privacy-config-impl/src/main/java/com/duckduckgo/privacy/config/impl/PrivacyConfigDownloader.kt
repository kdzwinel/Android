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

package com.duckduckgo.privacy.config.impl

import com.duckduckgo.app.global.plugins.PluginPoint
import com.duckduckgo.features.api.FeatureName
import com.duckduckgo.privacy.config.api.PrivacyConfigDownloader
import com.duckduckgo.privacy.config.network.PrivacyConfigService
import com.duckduckgo.privacy.config.plugins.PrivacyFeaturePlugin
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import timber.log.Timber

class RealPrivacyConfigDownloader(private val privacyConfigService: PrivacyConfigService, private val privacyFeaturePluginPoint: PluginPoint<PrivacyFeaturePlugin>) : PrivacyConfigDownloader {
    override fun download() : List<Pair<FeatureName, Boolean>> {
        Timber.d("Downloading privacy config")
        val response = runCatching {
            privacyConfigService.privacyConfig().execute()
        }.getOrElse {
            Timber.w("Error downloading tracker rules list: $it")
            Response.error(400, "".toResponseBody(null))
        }

        val features = mutableListOf<Pair<FeatureName, Boolean>>()

        response.body()?.features?.forEach { feature ->
            privacyFeaturePluginPoint.getPlugins().forEach { plugin ->
                val enabled = plugin.store(FeatureName(feature.key), feature.value)
                features.add(FeatureName(feature.key) to enabled)
            }
        }

        return features.toList()
    }
}
