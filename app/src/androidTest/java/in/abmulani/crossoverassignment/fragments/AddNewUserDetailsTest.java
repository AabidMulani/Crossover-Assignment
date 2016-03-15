package in.abmulani.crossoverassignment.fragments;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Created by aabid-personal on 3/15/16.
 */
public class AddNewUserDetailsTest extends TestCase {

    AddNewUserDetails addNewUserDetails;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        addNewUserDetails = new AddNewUserDetails();
    }

    @SmallTest
    public void testAllValidation() {

        assertTrue(addNewUserDetails.validatePhone("89123123"));
        assertTrue(addNewUserDetails.validatePhone("8912312398"));
        assertTrue(addNewUserDetails.validatePhone("89123123231"));
        assertTrue(addNewUserDetails.validatePhone("1"));


        assertFalse(addNewUserDetails.validatePhone(""));
        assertFalse(addNewUserDetails.validateEmail(""));
        assertFalse(addNewUserDetails.validateFirstName(""));
        assertFalse(addNewUserDetails.validateLastName(""));
        assertFalse(addNewUserDetails.validateBloodGroup(""));

        assertFalse(addNewUserDetails.validateEmail("asdasd"));
        assertFalse(addNewUserDetails.validateEmail("asdasd@"));
        assertFalse(addNewUserDetails.validateEmail("asdasdasd@acas"));
        assertFalse(addNewUserDetails.validateEmail("asdadsa@asdasd."));

        assertTrue(addNewUserDetails.validateEmail("asdadsa@asdasd.com"));


    }

}