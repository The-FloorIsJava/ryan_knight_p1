package main.java.ProfileDB;

import java.util.HashMap;
import java.util.Map;

public class TemporaryProfileMap implements ProfileDB {

    private final Map<String,ProfileInterface> profileMap;


    public TemporaryProfileMap() {
        profileMap = new HashMap<>();
    }

    @Override
    public void addProfile(ProfileInterface profileInterface) {
        profileMap.put(profileInterface.getUsername(),profileInterface);
    }

    @Override
    public void removeProfile(String username) {
        profileMap.remove(username,profileMap.get(username));
    }

    @Override
    public void removeProfile(ProfileInterface profileInterface) {
        removeProfile(profileInterface.getUsername());
    }

    @Override
    public void getProfile(String username) {

    }

    public void submitTicket(String username, double amount) {

    }
}
