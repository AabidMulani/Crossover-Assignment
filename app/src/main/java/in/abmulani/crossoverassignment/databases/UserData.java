package in.abmulani.crossoverassignment.databases;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class UserData extends SugarRecord {

    String firstname;
    String lastname;
    String email;
    String phone;
    String bloodGroup;
    boolean donor;
    double latitude;
    double longitude;

    public UserData() {
    }

    public UserData(String firstname, String lastname, String email, String phone, String bloodGroup, boolean donor, double latitude, double longitude) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.donor = donor;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public boolean isDonor() {
        return donor;
    }

    public void setDonor(boolean donor) {
        this.donor = donor;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static void addThisData(String firstname, String lastname, String email, String phone, String bloodGroup, boolean donor, double latitude, double longitude) {
        UserData userData = new UserData(firstname, lastname, email, phone, bloodGroup, donor, latitude, longitude);
        userData.save();
    }

    public static UserData getThisPointData(double latitude, double longitude) {
        Timber.i("Query: Latitude:: " + latitude + " :::: Longitude::" + longitude);
        List<UserData> userData = UserData.find(UserData.class, "latitude = ? and longitude = ?", new String[]{String.valueOf(latitude), String.valueOf(longitude)});
        if (userData != null && userData.size() > 0) {
            return userData.get(0);
        }
        return null;
    }

    public static List<UserData> getAllUserData() {
        List<UserData> userData = UserData.listAll(UserData.class);
        if (userData.size() > 0) {
            return userData;
        }
        return new ArrayList<>();
    }

    public static List<UserData> getAllDonorsData() {
        List<UserData> userData = UserData.findWithQuery(UserData.class, "Select * from USER_DATA where donor = ?", "true");
        if (userData != null && userData.size() > 0) {
            return userData;
        }
        return new ArrayList<>();
    }

    public static List<UserData> getAllPatientData() {
        List<UserData> userData = UserData.findWithQuery(UserData.class, "Select * from USER_DATA where donor = ?", "false");

        //UserData.find(UserData.class, "donor = ? ", new String[]{String.valueOf(false)});
        if (userData != null && userData.size() > 0) {
            return userData;
        }
        return new ArrayList<>();
    }

    public static void addDummyUserData() {
        UserData.addThisData("Aabid", "Mulani", "mulaniaabid@gmail.com", "8983394512", "A +", true, 8337339.5146306, 2395936.578212);
        UserData.addThisData("Alex", "R.", "alex@123.com", "5555555555", "A -", false, 8733604.011789259, 2745742.4797827154);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", donor=" + donor +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
