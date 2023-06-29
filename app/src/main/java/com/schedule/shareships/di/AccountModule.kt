package com.schedule.shareships.di

import com.schedule.shareships.data.sources.AccountService
import com.schedule.shareships.data.sources.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class AccountModule {

    @Binds
    @Singleton
    abstract fun bindAccountService(
        accountServiceImpl: AccountServiceImpl
    ): AccountService
}
