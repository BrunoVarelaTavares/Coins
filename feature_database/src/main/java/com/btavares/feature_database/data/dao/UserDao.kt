package com.btavares.feature_database.data.dao

import androidx.room.*
import com.btavares.feature_database.data.entities.*
import java.math.BigDecimal

@Dao
interface UserDao {


    @Query("INSERT INTO users (name, email) VALUES (:userFullName, :userEmail)")
    suspend fun  insertUser(userFullName : String, userEmail : String) : Long

    @Query("INSERT INTO balanceportfolio (balanceId, portfolioId) VALUES (:balanceId, :portfolioId)")
    suspend fun  insertBalancePortfolio(balanceId: Long, portfolioId : Long)

    @Query("INSERT INTO portfoliocryptocurrency (portfolioId, cryptocurrencyId) VALUES (:portfolioId, :cryptocurrencyId)")
    suspend fun  insertPortfolioCurrency(portfolioId: Long, cryptocurrencyId : String)

    @Query("SELECT portfolioId FROM portfoliocryptocurrency WHERE cryptocurrencyId == :cryptoId")
    suspend fun getPortfolioId(cryptoId: String) : Long?

    @Query("SELECT cryptocurrencyId FROM portfoliocryptocurrency WHERE portfolioId == :portfolioId")
    suspend fun getCryptocurrencyId(portfolioId: Long) : String

    @Query("UPDATE portfolio SET native_currency_value = :nativeCurrencyValue, cryptocurrency_value = :cryptoCurrencyValue  WHERE portfolioId = :portfolioId")
    suspend fun updatePortfolio(portfolioId: Long ,nativeCurrencyValue: Double ,cryptoCurrencyValue : String)

    @Query("UPDATE portfolio SET cryptocurrency_value = :cryptoCurrencyValue  WHERE portfolioId = :portfolioId")
    suspend fun updatePortfolioCryptoCurrencyValue(portfolioId: Long, cryptoCurrencyValue : String)

    @Query("SELECT coin_price FROM cryptocurrency WHERE cryptocurrencyId == :cryptoId")
    suspend fun getCryptocurrencyCurrentPrice(cryptoId: String) : Double

    @Query("SELECT SUM(native_currency_value) FROM portfolio")
    suspend fun portfolioTotal() : Double

    @Query("SELECT balanceId FROM balance LIMIT 1")
    suspend fun getBalanceId() : Long

    @Query("UPDATE balance SET totalBalance = :total WHERE balanceId = :id")
    suspend fun updateTotalBalance(id: Long ,total : Double)

    @Query("SELECT portfolioId FROM portfolio LIMIT 1")
    suspend fun getPortfolioId() : Long

    @Query("SELECT native_currency_value FROM portfolio WHERE portfolioId = :portfolioId")
    suspend fun getPortfolioNativeCurrencyValue(portfolioId: Long) : Double

    @Query("SELECT COUNT(*) FROM cryptocurrency")
    suspend fun getCryptocurrency() : Int

    @Query("SELECT * FROM portfolio")
    suspend fun getAllPortfolio() : List<Portfolio>

    @Query("UPDATE portfolio SET  native_currency_value = :nativeCurrencyValue WHERE portfolioId = :portfolioId")
    suspend fun updatePortfolioNativeCurrencyValue(portfolioId: Long, nativeCurrencyValue: Double)

    @Query("SELECT cryptocurrencyId FROM portfoliocryptocurrency WHERE portfolioId == :id")
    suspend fun getPortfolioCrytocurrency(id: Long) : String

    @Query("SELECT * FROM cryptocurrency WHERE cryptocurrencyId == :cryptoId")
    suspend fun getCrytocurrencyById(cryptoId: String) : Cryptocurrency

    @Query("UPDATE cryptocurrency SET coin_price = :price WHERE cryptocurrencyId = :id")
    suspend fun updateCryptocurrencyPrice(id: String ,price : Double?)


    @Query("SELECT id FROM users where name == :userName And email == :userEmail")
    suspend fun getUserId(userName : String, userEmail : String): Long

    @Query("INSERT INTO balance (user_id) VALUES (:userId)")
    suspend fun insertBalance(userId: Long)

    @Query("INSERT INTO portfolio DEFAULT VALUES")
    suspend fun insertPortfolio() : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortfolio(portfolio: Portfolio) : Long

    @Query("SELECT * from users where id == :id")
    suspend fun getUser(id: Long) : User

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT currencyCode FROM usernativecurrency WHERE id == :userId")
    suspend fun getNativeUserCurrency(userId: Long): String

    @Query("SELECT * FROM native_currency ORDER BY currencyCode ASC")
    suspend fun getNativeCurrencies(): List<NativeCurrency>

    @Insert
    suspend fun insertUserNativeCurrency(userNativeCurrency: UserNativeCurrency)

    @Query("SELECT * FROM users where id == :id")
    suspend fun getUserNativeCurrency(id: Long): UserWithNativeCurrency

    @Query("SELECT cryptocurrencyId FROM usercryptocurrency where id == :id")
    suspend fun getUserCryptocurrencies(id: Long): List<String>

    @Query("SELECT EXISTS(SELECT * FROM usercryptocurrency where id == :id And  cryptocurrencyId == :cryptocurrencyId)")
    suspend fun checkIfCryptocurrencyExistsInWatchlist(id: Long, cryptocurrencyId: String): Boolean

    @Insert
    suspend fun insertCryptocurrencyInWatchlist(userCryptocurrency : UserCryptocurrency)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCryptocurrency(cryptocurrency : Cryptocurrency)

    @Delete
    suspend fun deleteCryptocurrencyInWatchlist(userCryptocurrency : UserCryptocurrency)

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUserBalance(): UserPortfolio

    @Query("UPDATE usernativecurrency set currencyCode = :currencyCode where id == :id")
    suspend fun updateUserNativeCurrency(id : Long, currencyCode :String)



/*    //Native Currency
    @Transaction
    @Query("SELECT * FROM  users where id == :id")
    fun getUserNativeCurrency(id: Long) : UserWithNativeCurrency

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserNativeCurrent(userNativeCurrencyCR: UserNativeCurrency)

    @Query("UPDATE usernativecurrency SET nativeCurrencyId =:currencyId WHERE userId =:id")
    fun updateNativeCurrency(id: Long, currencyId: Long);


    //User Currency WatchList
    @Transaction
    @Query("SELECT * FROM  users where id == :id")
    fun getUserCryptocurrencyWatchlist(id: Long) : UserWithCryptocurrencyWatchlist

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCryptocurrencyToWatchlist(userCryptoReference: UserCryptocurrency)

    @Delete
    fun removeFromCryptocurrencyToWatchlist(userCryptoReference: UserCryptocurrency)


    // User Portfolio
    @Transaction
    @Query("SELECT * FROM  users where id == :id")
    fun getUserPortfolioBalance(id: Long) : UserWithPortfolioBalance*/
}