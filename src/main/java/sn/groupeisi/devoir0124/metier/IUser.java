package sn.groupeisi.devoir0124.metier;

import sn.groupeisi.devoir0124.entities.User;

public interface IUser {
    public User doConnexion(String login, String password);
}
