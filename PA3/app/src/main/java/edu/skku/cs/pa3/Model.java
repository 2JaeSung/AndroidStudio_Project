package edu.skku.cs.pa3;

import android.app.Activity;

public class Model implements Contract.ContractForModel{
    private String id;
    private String sid;
    private Activity activity;


    private String name;
    private String passwd;
    private Boolean success;

    private Integer map_1;
    private Integer map_2;



    private String rank1_name;
    private String rank2_name;
    private String rank3_name;

    private Integer rank1_map1;
    private Integer rank2_map1;
    private Integer rank3_map1;

    private Integer rank1_map2;
    private Integer rank2_map2;
    private Integer rank3_map2;

    public Model(){
    }

    public Model(String id, String sid, Activity activity){
        this.id = id;
        this.sid = sid;
        this.activity = activity;
    }



    @Override
    public String getId(){
        return this.id;
    }

    @Override
    public String getSid(){
        return this.sid;
    }

    @Override
    public Activity getActivity(){
        return this.activity;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setMap_1(Integer map_1) {
        this.map_1 = map_1;
    }

    public void setMap_2(Integer map_2) {
        this.map_2 = map_2;
    }


    public String getRank1_name() {
        return rank1_name;
    }

    public String getRank2_name() {
        return rank2_name;
    }

    public String getRank3_name() {
        return rank3_name;
    }

    public Integer getRank1_map1() {
        return rank1_map1;
    }

    public Integer getRank1_map2() {
        return rank1_map2;
    }

    public Integer getRank2_map1() {
        return rank2_map1;
    }

    public Integer getRank2_map2() {
        return rank2_map2;
    }

    public Integer getRank3_map1() {
        return rank3_map1;
    }

    public Integer getRank3_map2() {
        return rank3_map2;
    }
}
