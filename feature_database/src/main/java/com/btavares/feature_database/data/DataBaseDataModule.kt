package com.btavares.feature_database.data

import com.btavares.feature_database.MODULE_NAME
import com.btavares.feature_database.data.db.CoinsDatabase
import org.kodein.di.Kodein

import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal  val dataModule = Kodein.Module("${MODULE_NAME}DataModule"){

    bind() from singleton { CoinsDatabase(instance()) }

    bind() from singleton { instance<CoinsDatabase>().userDao() }

}