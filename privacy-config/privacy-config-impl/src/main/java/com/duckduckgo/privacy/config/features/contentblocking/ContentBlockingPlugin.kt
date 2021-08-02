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
import com.duckduckgo.privacy.config.store.ContentBlockingException
import com.duckduckgo.privacy.config.store.PrivacyConfigDatabase
import com.squareup.anvil.annotations.ContributesMultibinding
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

@ContributesMultibinding(AppObjectGraph::class)
class ContentBlockingPlugin @Inject constructor(
    privacyConfigDatabase: PrivacyConfigDatabase
) : PrivacyFeaturePlugin {

    private val contentBlockingDao = privacyConfigDatabase.contentBlockingDao()

    override fun store(featureName: FeatureName, jsonObject: JSONObject?): Boolean {
        if (featureName.value == this.featureName.value) {
            val contentBlockingExceptions = mutableListOf<ContentBlockingException>()
            Timber.d("MARCOS works!")
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<ContentBlocking> =
                moshi.adapter(ContentBlocking::class.java)

            val contentBlocking: ContentBlocking? = jsonAdapter.fromJson(jsonObject.toString())
            contentBlocking?.exceptions?.map {
                contentBlockingExceptions.add(ContentBlockingException(it.domain, it.reason))
            }
            contentBlockingDao.deleteAll()
            contentBlockingDao.insertAll(contentBlockingExceptions)
            return contentBlocking?.state == "enabled"
        }
        return false
    }

    override val featureName: FeatureName
        get() = FeatureName("contentBlocking")
}
