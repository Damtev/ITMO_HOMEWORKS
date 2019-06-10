package ru.ifmo.rain.kamenev.bank;

import ru.ifmo.rain.kamenev.bank.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {
    /**
     * Creates a new account with specified identifier if it is not already exists.
     * @param id account id
     * @return created or existing account.
     */
    Account createAccount(String id) throws RemoteException;

    /**
     * Returns account by identifier.
     * @param id account id
     * @return account with specified identifier or {@code null} if such account does not exists.
     */
    Account getAccount(String id) throws RemoteException;

    Person savePerson(final String name, final String surname, final String passport) throws RemoteException;

    Person getPerson(final String passport, final String mode) throws RemoteException;
}
