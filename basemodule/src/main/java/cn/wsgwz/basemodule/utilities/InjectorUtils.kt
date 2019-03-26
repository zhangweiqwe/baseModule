/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.wsgwz.basemodule.utilities

import android.content.Context
import cn.wsgwz.basemodule.data.BaseAppDatabase
import cn.wsgwz.basemodule.data.SuspensionWindowRepository
import cn.wsgwz.basemodule.data.UserRepository

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository.getInstance(BaseAppDatabase.getInstance(context = context).userDao())
    }

    fun provideSuspensionWindowRepository(context: Context): SuspensionWindowRepository {
        return SuspensionWindowRepository.getInstance(BaseAppDatabase.getInstance(context = context).suspensionWindowDao())
    }

}
