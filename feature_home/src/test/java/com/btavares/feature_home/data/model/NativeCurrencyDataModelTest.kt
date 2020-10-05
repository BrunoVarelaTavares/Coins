package com.btavares.feature_home.data.model

import org.junit.Test
import com.btavares.feature_home.data.DataFixtures
import com.btavares.feature_home.domain.model.NativeCurrencyDomainModel
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo

class NativeCurrencyDataModelTest{

    @Test
    fun `dataModel to NativeCurrencyDomainModel `(){

        val nativeCurrency = DataFixtures.getNativeCurrency()

        val nativeCurrencyDomain = nativeCurrency.toDomainModel()

        nativeCurrencyDomain shouldBeEqualTo NativeCurrencyDomainModel(
            nativeCurrency.currencyCode,
            nativeCurrency.currencySymbol
        )
    }
}