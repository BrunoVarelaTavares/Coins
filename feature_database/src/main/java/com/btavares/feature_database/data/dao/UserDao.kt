package com.btavares.feature_database.data.dao

import androidx.room.*
import com.btavares.feature_database.data.entities.*

@Dao
interface UserDao {

    //User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertUser(user : User) : Long

    @Query("SELECT * from users where id == :id")
    suspend fun getUser(id: Long) : User

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM native_currency")
    suspend fun getNativeCurrency(): NativeCurrency

    @Query("SELECT * FROM native_currency")
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

    @Insert
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