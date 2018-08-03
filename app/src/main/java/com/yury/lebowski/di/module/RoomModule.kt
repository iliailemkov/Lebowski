package com.yury.lebowski.di.module

import android.app.Application
import com.yury.lebowski.data.LebowskiDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = LebowskiDb.getDataBase(app)

    @Provides
    @Singleton
    fun provideAccoundDao(db: LebowskiDb) = db.accountDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: LebowskiDb) = db.categoryDao()

    /*@Provides
    @Singleton
    fun provideOperationDao(db: LebowskiDb) = db.operationDao()*/
}