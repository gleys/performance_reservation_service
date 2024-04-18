package com.example.performance_reservation.domain.wallet;

import com.example.performance_reservation.domain.BaseEntity;
import com.example.performance_reservation.domain.user.User;
import com.example.performance_reservation.domain.wallet.exception.AmountInsufficientException;
import com.example.performance_reservation.domain.wallet.exception.AmountInvalidException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Wallet extends BaseEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    private long userId;

    private int amount;

    public int getBalance() {
        return amount;
    }

    public void pay(final int amount) {
        if (this.amount < amount)  {
            throw AmountInsufficientException.EXCEPTION;
        }
        this.amount -= amount;
    }

    public void charge(final int amount) {
        if (amount <= 0)  {
            throw AmountInvalidException.EXCEPTION;
        }
        this.amount += amount;
    }
}