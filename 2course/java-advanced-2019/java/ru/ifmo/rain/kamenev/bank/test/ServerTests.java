package ru.ifmo.rain.kamenev.bank.test;

import org.junit.Test;
import ru.ifmo.rain.kamenev.bank.*;

import java.rmi.RemoteException;
import static org.junit.Assert.*;

public class ServerTests {

    @Test
    public void test1() {
        Server server = new Server();
        try {
            assertNotNull(Utils.get("//localhost/bank"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
