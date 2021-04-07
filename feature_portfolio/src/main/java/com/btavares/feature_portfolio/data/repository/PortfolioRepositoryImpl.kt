package com.btavares.feature_portfolio.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.getCurrencyCode
import com.btavares.feature_database.data.entities.getCurrencySymbol
import com.btavares.feature_portfolio.data.model.NativeCurrencyDataModel
import com.btavares.feature_portfolio.data.model.PortfolioCryptocurrencyDataModel
import com.btavares.feature_portfolio.data.model.toDomainModel
import com.btavares.feature_portfolio.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_portfolio.domain.model.PortfolioCryptocurrencyDomainModel
import com.btavares.feature_portfolio.domain.repository.PortfolioRepository
import java.math.BigDecimal
import java.math.RoundingMode


internal class PortfolioRepositoryImpl(
    private val userSharePreferences: UserSharePreferences,
    private val userDao: UserDao
) : PortfolioRepository {

    override suspend fun getAllCryptocurrencies() = convertToPortfolioCryptocurrencyDomainModel()

    override suspend fun getPortfolioBalance() : Double =
        userDao.getUserBalance().balance?.totalBalance ?: 0.0

    override suspend fun savePortfolioNativeCurrencyValue(cryptoId: String, nativeCurrencyValue: Double) {
        val portfolioId = userDao.getPortfolioId(cryptoId)
        val cryptocurrencyCurrentValue = userDao.getCryptocurrencyCurrentPrice(cryptoId)
        val cryptoCurrencyValue = BigDecimal(nativeCurrencyValue).divide(BigDecimal(cryptocurrencyCurrentValue), 8, RoundingMode.HALF_EVEN)
        userDao.updatePortfolio(portfolioId!!.toLong(),nativeCurrencyValue, cryptoCurrencyValue.toString())
        userDao.updateTotalBalance(userDao.getBalanceId(), userDao.portfolioTotal())
        
    }

    override suspend fun getNativeCurrency(): NativeCurrencyDomainModel {
        val userCurrency = userDao.getUserNativeCurrency(userSharePreferences.getUserId())
        return NativeCurrencyDataModel(userCurrency.getCurrencyCode(), userCurrency.getCurrencySymbol()).toDomainModel()
    }



    private suspend fun convertToPortfolioCryptocurrencyDomainModel() : List<PortfolioCryptocurrencyDomainModel> {
        val portfolio = userDao.getAllPortfolio()
        val list = mutableListOf<PortfolioCryptocurrencyDomainModel>()

        portfolio.forEach {
            val crypto = userDao.getCrytocurrencyById(userDao.getPortfolioCrytocurrency(it.portfolioId))
            val portfolioCryptocurrencyDataModel = PortfolioCryptocurrencyDataModel(
                id = crypto.cryptocurrencyId,
                name = crypto.name,
                imageUrl = crypto.imageUrl,
                description = crypto.coinDescription,
                percentage = crypto.coinPercentage,
                symbol = crypto.coinSymbol,
                nativeCurrencyValue = it.nativeCurrencyValue,
                cryptocurrencyValue = it.cryptocurrencyValue
            ).toDomainModel()

            list.add(portfolioCryptocurrencyDataModel)
      }

     return list.toList()

    }

}

