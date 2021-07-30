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

import com.duckduckgo.features.api.FeatureCustomConfigPlugin
import com.duckduckgo.features.api.FeatureName
import com.duckduckgo.privacy.config.api.PrivacyConfigDownloader
import timber.log.Timber

class PrivacyConfigPlugin(private val privacyConfigDownloader: PrivacyConfigDownloader): FeatureCustomConfigPlugin {

    override fun download(): List<Pair<FeatureName, Boolean>> {
        Timber.d("Download from privacy config plugin")
        privacyConfigDownloader.download()
        return listOf(FeatureName("test") to true)
    }

}