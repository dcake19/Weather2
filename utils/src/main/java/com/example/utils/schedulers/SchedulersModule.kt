package com.example.utils.schedulers

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulersModule {

    @Provides
    @Singleton
    fun provideRxSchedulerProvider(): RxSchedulerProvider{
        return RxSchedulerProviderImpl()
    }


}