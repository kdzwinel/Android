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

package com.duckduckgo.privacy.config.features.contentblocking

import com.duckduckgo.di.scopes.AppObjectGraph
import com.duckduckgo.features.api.FeatureName
import com.duckduckgo.privacy.config.plugins.PrivacyFeaturePlugin
import com.squareup.anvil.annotations.ContributesMultibinding
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@ContributesMultibinding(AppObjectGraph::class)
class ContentBlockingPlugin @Inject constructor(

) : PrivacyFeaturePlugin {

    override fun store(featureName: FeatureName, jsonObject: JSONObject?): Boolean {
        if (featureName.value == this.featureName.value) {
            Timber.d("MARCOS works!~")
            return true
        }
        return false
    }

    override val featureName: FeatureName
        get() = FeatureName("contentBlocking")
}
