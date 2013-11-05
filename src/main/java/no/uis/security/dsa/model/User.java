package no.uis.security.dsa.model;

import no.uis.security.common.model.Entity;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity
@Table
public class User extends Entity<Long> {
    @Column
    private String userName;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @OneToMany(mappedBy = "user")
    private List<UserKeys> userKeys;

    public User(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<UserKeys> getUserKeys() {
        if (userKeys == null) {
            userKeys = new ArrayList<UserKeys>();
        }
        return userKeys;
    }

    public boolean addUserKeys(UserKeys userKeys) {

        return getUserKeys().add(userKeys);
    }

    public boolean removeUserKeys(UserKeys userKeys) {

        return getUserKeys().remove(userKeys);
    }
}
