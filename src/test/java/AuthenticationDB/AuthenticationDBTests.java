package test.java.AuthenticationDB;

import main.java.AuthenticationDB.AuthenticationDB;
import main.java.AuthenticationDB.TemporaryPasswordMapDB;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;

public class AuthenticationDBTests {

    // Generic usernames (emails) to test with.
    private final String genericUserName1 = "oliver.cromwell@gov.uk";
    private final String genericUserName2 = "jean.de_arc@gouv.fr";

    // Generic passwords to test with.
    private final String genericPassword1 = "t77uU##$$$__...";
    private final String genericPassword2 = "22###s.,!Hh";

    /**
     * Confirm that addNewUserMethod does not add user if the username is already taken.
     * */
    @Test
    public void testAddNewUser(){
        AuthenticationDB toTest = new TemporaryPasswordMapDB();
        toTest.addNewUser(genericUserName1,genericPassword1);

        assertThrows(IllegalArgumentException.class,()->toTest.addNewUser(genericUserName1,genericPassword2));
    }

    /**
     * Confirm verifyUsernameAndPassword method returns true if password is correct, false if it is
     * not correct, and throws IllegalArgumentException if username is not in database.
     * */
    @Test
    public void testVerifyUsernameAndPassword() {
        AuthenticationDB toTest = new TemporaryPasswordMapDB();

        toTest.addNewUser(genericUserName1,genericPassword1);

        // Confirm method returns true if given correct username and password.
        assertTrue(toTest.verifyUsernameAndPassword(genericUserName1,genericPassword1));

        // Confirm method returns false if given valid username and incorrect password.
        assertFalse(toTest.verifyUsernameAndPassword(genericUserName1,genericPassword2));

        // Confirm method throws illegal argument exception if the username is not in the database
        assertThrows(IllegalArgumentException.class, ()->toTest.verifyUsernameAndPassword(genericUserName2,genericPassword2));
    }

}
