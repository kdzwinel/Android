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

package com.duckduckgo.app.config

import androidx.work.ListenableWorker
import com.duckduckgo.app.global.plugins.worker.WorkerInjectorPlugin
import com.duckduckgo.di.scopes.AppObjectGraph
import com.duckduckgo.features.api.Feature
import com.duckduckgo.privacy.config.impl.Test
import com.squareup.anvil.annotations.ContributesMultibinding
import timber.log.Timber
import javax.inject.Inject

@ContributesMultibinding(AppObjectGraph::class)
class RemoteConfigDownloadWorkerPlugin @Inject constructor(
    private val feature: Feature,
    private val test: Test
) : WorkerInjectorPlugin {
    override fun inject(worker: ListenableWorker): Boolean {
        test.test()
        if (worker is RemoteConfigDownloadWorker) {
            worker.feature = feature
            Timber.v("Injecting dependencies for PrivacyConfigDownloadWorker")
            return true
        }

        return false
    }
}
