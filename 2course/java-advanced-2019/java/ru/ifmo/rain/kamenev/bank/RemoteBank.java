package ru.ifmo.rain.kamenev.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RemoteBank implements Bank {
    private final int port;
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Person> persons = new ConcurrentHashMap<>();

    public RemoteBank(final int port) {
        this.port = port;
    }

    public Account createAccount(final String id) throws RemoteException {
        System.out.println("Creating account " + id);
        final Account account = new RemoteAccount(id);
        if (accounts.putIfAbsent(id, account) == null) {
            UnicastRemoteObject.exportObject(account, port);
            return account;
        } else {
            return getAccount(id);
        }
    }

    public Account getAccount(final String id) {
        System.out.println("Retrieving account " + id);
        return accounts.get(id);
    }

    public Person savePerson(final String name, final String surname, final String passport) throws RemoteException {
        final Person curPerson = new RemotePerson(name, surname, passport);
        if (persons.putIfAbsent(curPerson.getPassport(), curPerson) == null) {
            UnicastRemoteObject.exportObject(curPerson, port);
            return curPerson;
        } else {
            return getPerson(passport, "remote");
        }
    }

    public Person getPerson(final String passport, final String mode) throws RemoteException {
        final Person curPerson = persons.get(passport);
        if (curPerson == null) {
            return null;
        }
        if (mode.equals("remote")) {
            return curPerson;
        } else if (mode.equals("local")) {
            Person local = new LocalPerson(curPerson.getName(), curPerson.getSurname(), curPerson.getPassport());
            for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
                if (accountEntry.getKey().startsWith(passport)) {
                    String[] ids = accountEntry.getKey().split(":");
                    local.changeAccount(ids[1], accountEntry.getValue().getAmount(), this);
                }
            }
            return local;
        } else {
            return null;
        }
    }
}
