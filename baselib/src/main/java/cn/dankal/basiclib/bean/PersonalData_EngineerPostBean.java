package cn.dankal.basiclib.bean;

import java.io.Serializable;

public class PersonalData_EngineerPostBean implements Serializable {

    /**
     * name :
     * avatar :
     * competence :
     * province :
     * city :
     * mobile :
     */

    private String name;
    private String avatar;
    private String competence;
    private String province;
    private String city;
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
