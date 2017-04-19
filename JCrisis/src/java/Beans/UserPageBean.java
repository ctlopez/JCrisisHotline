/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;

/**
 * Java Bean for User page data.
 *
 * @author DragonSheep
 *
 * Updated: 2017/04/18 By: Alissa Duffy Standardized Commenting.
 */
public class UserPageBean {

    /**
     * Array List of Users.
     */
    ArrayList<User> userList;
    /**
     * Current User.
     */
    User currentUser;
    /**
     * Array List of roles.
     */
    ArrayList<String> roles;
    /**
     * Error Message.
     */
    String errorMessage;

    /**
     * Get Error Message.
     *
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set Error Message.
     *
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Get Array List of Users.
     *
     * @return userList
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    /**
     * Set Array List of Users.
     *
     * @param userList
     */
    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    /**
     * Get Current User.
     *
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set Current User.
     *
     * @param currentUser
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * UserPageBean Empty Constructor.
     */
    public UserPageBean() {
        errorMessage = "";
    }

    /**
     * UserPageBean Constructor for Array List of Users and Current User.
     *
     * @param userList
     * @param currentUser
     */
    public UserPageBean(ArrayList<User> userList, User currentUser) {
        this.userList = userList;
        this.currentUser = currentUser;
        errorMessage = "";
    }

    /**
     * Get Array List of Roles.
     *
     * @return roles
     */
    public ArrayList<String> getRoles() {
        return roles;
    }

    /**
     * Set Array List of Roles
     *
     * @param Roles
     */
    public void setRoles(ArrayList<String> Roles) {
        this.roles = Roles;
    }

    /**
     * Data Entry User
     *
     * @return roles.contains("Data Entry");
     */
    public boolean isDataEntry() {
        return null != roles && roles.contains("dataEntry");
    }

    /**
     * Manager User
     *
     * @return roles.contains("Manager");
     */
    public boolean isManager() {
        return null != roles && roles.contains("manager");
    }
    
    public String getUserListJSON() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        boolean isFirst = true;
        for(User listedUser:this.userList) {
            if(isFirst){
                isFirst = false;
            } else {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\""+listedUser.ID +"\":{");
            jsonBuilder.append("\"addressOne\":\""+listedUser.addressOne+"\",");
            jsonBuilder.append("\"addressTwo\":\""+listedUser.addressTwo+"\",");
            jsonBuilder.append("\"city\":\""+listedUser.city+"\",");
            jsonBuilder.append("\"firstName\":\""+listedUser.firstName+"\",");
            jsonBuilder.append("\"phone\":\""+listedUser.phone+"\",");
            jsonBuilder.append("\"territory\":\""+listedUser.territory+"\",");
            jsonBuilder.append("\"userName\":\""+listedUser.userName+"\",");
            jsonBuilder.append("\"zip\":\""+listedUser.zip+"\",");
            jsonBuilder.append("\"isReports\":\""+listedUser.getRoles().contains("reports")+"\",");
            jsonBuilder.append("\"isCouncelor\":\""+listedUser.getRoles().contains("Councelor")+"\",");
            jsonBuilder.append("\"isManager\":\""+listedUser.getRoles().contains("Manager")+"\",");
            jsonBuilder.append("\"isDataEntry\":\""+listedUser.getRoles().contains("DataEntry")+"\",");
            jsonBuilder.append("}");
            
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
