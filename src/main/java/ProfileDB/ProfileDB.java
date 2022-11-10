package main.java.ProfileDB;

public interface ProfileDB {
    void addProfile(ProfileInterface profileInterface);
    void removeProfile(String username);
    void removeProfile(ProfileInterface profileInterface);

    void getProfile(String username);

}
