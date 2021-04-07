package com.btavares.feature_home.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.*
import com.btavares.feature_home.data.model.CryptocurrencyMarketDataModel
import com.btavares.feature_home.data.model.NativeCurrencyDataModel
import com.btavares.feature_home.data.model.toDomainModel
import com.btavares.feature_home.data.remote.service.CoingeckoService
import com.btavares.feature_home.data.remote.service.CryptoControlNewsService
import com.btavares.feature_home.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_home.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_home.domain.repository.HomeRepository
import java.math.BigDecimal
import java.math.RoundingMode

internal class HomeRepositoryImpl(
    private val userSharePreferences: UserSharePreferences,
    private val coingeckoService: CoingeckoService,
    private val cryptoControlNewsService: CryptoControlNewsService,
    private val userDao: UserDao) : HomeRepository {

     override suspend fun getMarketDataAsync(
         currency: String?,
         ids: String?
     ) : List<CryptocurrencyMarketDomainModel> {
         val result = coingeckoService.getMarketDataAsync(currency, ids)
         return result.map { it.toDomainModel() }

     }

    override suspend fun getAllMarketDataAsync(
        currency: String?
    ): List<CryptocurrencyMarketDomainModel> {
        val cryptoMarketData = coingeckoService.getMarketDataAsync(currency)
        saveCryptocurrencies(cryptoMarketData)
        return cryptoMarketData.map { it.toDomainModel() }
    }

    override suspend fun getCryptocurrenciesTopNewsAsync() =
        cryptoControlNewsService.getCryptocurrenciesNewsAsync(
        false
    ).map { it.toDomainModel() }

    override suspend fun getCryptocurrenciesLatestNewsAsync() =
        cryptoControlNewsService.getCryptocurrenciesNewsAsync(
            true
        ).map { it.toDomainModel() }

    override suspend fun getNativeCurrency(): NativeCurrencyDomainModel {
        val userCurrency = userDao.getUserNativeCurrency(userSharePreferences.getUserId())
        return NativeCurrencyDataModel(userCurrency.getCurrencyCode(), userCurrency.getCurrencySymbol()).toDomainModel()
    }

    override suspend fun getUserBalance(): Double =
         userDao.getUserBalance().balance?.totalBalance ?: 0.0


    override suspend fun getCryptocurrenciesWatchlistIds() =
        userDao.getUserCryptocurrencies(userSharePreferences.getUserId())

    override suspend fun checkIfUserExists() = userSharePreferences.checkIfUserInfoExists()

    override suspend fun insertUser(userFullName: String, userEmail: String) : Long {
        userDao.insertUser(userFullName,userEmail)
        val userId = userDao.getUserId(userFullName, userEmail)
        setUpNewUserSettings(userId)
        userDao.insertBalance(userId)
/*        val balanceId = userDao.getBalanceId()
        val portfolioId = userDao.getPortfolioId()
        userDao.insertBalancePortfolio(balanceId = balanceId, portfolioId = portfolioId)*/

        return userId
    }

    private suspend fun setUpNewUserSettings(userId : Long?){
        if (userId != null && userId > 0){
            userDao.insertUserNativeCurrency(UserNativeCurrency(userId))
            userSharePreferences.saveUserId(userId)
        }
    }

    private suspend fun saveCryptocurrencies(coins : List<CryptocurrencyMarketDataModel>) {
        val count = userDao.getCryptocurrency()
        if (count == 0) {
            val balanceId = userDao.getBalanceId()
            coins.forEach {
                saveCryptocurrency(it, balanceId)
                }
        } else {
            coins.forEach {
                val portfolioId = userDao.getPortfolioId(it.id.toString())
                if (portfolioId == null){
                    val balanceId = userDao.getBalanceId()
                    saveCryptocurrency(it, balanceId)
                }
                updatePortfolioCryptocurrencies(it.id, it.currentPrice)
            }
        }
    }


    private suspend fun saveCryptocurrency(cryptocurrency : CryptocurrencyMarketDataModel, balanceId: Long) {
        val portfolioId = userDao.insertPortfolio()
        userDao.insertBalancePortfolio(balanceId = balanceId, portfolioId = portfolioId)
        userDao.insertPortfolioCurrency(portfolioId, cryptocurrency.id.toString())
        userDao.insertCryptocurrency(Cryptocurrency(cryptocurrency.id.toString(),cryptocurrency.name.toString(), cryptocurrency.image.toString(),
            cryptocurrency.symbol.toString(), "", "", cryptocurrency.currentPrice!!))
    }


    private suspend fun updatePortfolioCryptocurrencies(cryptoId : String?, cryptoCurrentPrice : Double?) {
        if(cryptoId.toString().isNotEmpty()) {
            val portfolioId = userDao.getPortfolioId(cryptoId.toString())
            val nativeCurrencyValue = userDao.getPortfolioNativeCurrencyValue(portfolioId!!)
            if (nativeCurrencyValue > 0){
                val cryptoCurrencyValue = BigDecimal(nativeCurrencyValue).divide(BigDecimal(cryptoCurrentPrice!!.toDouble()), 8, RoundingMode.HALF_EVEN)
                userDao.updatePortfolioCryptoCurrencyValue(portfolioId, cryptoCurrencyValue.toString())
            }

            userDao.updateCryptocurrencyPrice(cryptoId.toString(),cryptoCurrentPrice)

        }

    }
}