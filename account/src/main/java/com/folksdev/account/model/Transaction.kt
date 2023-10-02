package com.folksdev.account.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Transaction(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String?,
        val transactionType: TransactionType? = TransactionType.INITAL,
        val amount: BigDecimal?,
        val transactionDate: LocalDateTime?,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "account_id", nullable = false)
        val account: Account


) {
    constructor(amount: BigDecimal, account: Account) : this(
            id = null,
            amount = amount,
            transactionDate = LocalDateTime.now(),
            transactionType = TransactionType.INITAL,
            account = account
    )


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (id != other.id) return false
        if (transactionType != other.transactionType) return false
        if (amount != other.amount) return false
        if (transactionDate != other.transactionDate) return false
        if (account != other.account) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (transactionType?.hashCode() ?: 0)
        result = 31 * result + (amount?.hashCode() ?: 0)
        result = 31 * result + (transactionDate?.hashCode() ?: 0)
        //customerda accountu yoruma aldığımız için burda da aldık
        //result = 31 * result + account.hashCode()
        return result
    }
}

enum class TransactionType {
    INITAL, TRANSFER
}


