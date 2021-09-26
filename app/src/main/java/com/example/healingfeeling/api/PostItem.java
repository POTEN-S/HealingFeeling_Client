package com.example.healingfeeling.api;

public class PostItem {
    private String happysongtitle;
    private String happysongratings;
    private String happybooktitle;
    private String happybookratings;
    private String happywheretitle;
    private String happywhereratings;
    private String sadsongtitle;
    private String sadsongratins;
    private String sadbooktitle;
    private String sadbookratings;
    private String sadwheretitle;
    private String sadwhereratings;
    private String angrysongtitle;
    private String angrysongratings;
    private String angrybooktitle;
    private String angrybookratings;
    private String angrywheretitle;
    private String angrywhereratings;

    public PostItem(String happysongtitle, String happysongratings, String happybooktitle, String happybookratings,
                    String happywheretitle,String happywhereratings,String sadsongtitle,String sadsongratins,String sadbooktitle,String sadbookratings,
                    String sadwheretitle,String sadwhereratings,String angrysongtitle,String angrysongratings,String angrybooktitle,String angrybookratings,String angrywheretitle,
                    String angrywhereratings){
        this.happysongtitle=happysongtitle;
        this.happysongratings=happysongratings;
        this.happybooktitle=happybooktitle;
        this.happybookratings=happybookratings;
        this.happywheretitle=happywheretitle;
        this.happywhereratings=happywhereratings;
        this.sadsongtitle=sadsongtitle;
        this.sadsongratins=sadsongratins;
        this.sadbooktitle=sadbooktitle;
        this.sadbookratings=sadbookratings;
        this.sadwheretitle=sadwheretitle;
        this.sadwhereratings=sadwhereratings;
        this.angrysongtitle=angrysongtitle;
        this.angrysongratings=angrysongratings;
        this.angrybooktitle=angrybooktitle;
        this.angrybookratings=angrybookratings;
        this.angrywheretitle=angrywheretitle;
        this.angrywhereratings=angrywhereratings;


    }

    public String gethappysongtitle() {
        return happysongtitle;
    }

    public String getHappysongratings(){
        return happysongratings;
    }

    public String gethappybooktitle() {
        return happybooktitle;
    }

    public String getHappybookratings(){
        return happybookratings;
    }

    public String getHappywheretitle() {
        return happywheretitle;
    }

    public String getHappywhereratings(){
        return happywhereratings;
    }
    ////sad

    public String getsadsongtitle() {
        return sadsongtitle;
    }

    public String getsadsongratings(){
        return sadsongratins;
    }
    public String getsadbooktitle() {
        return sadbooktitle;
    }

    public String getsadbookratings(){
        return sadbookratings;
    }

    public String getsadwheretitle() {
        return sadwheretitle;
    }

    public String getsadwhereratings(){
        return sadwhereratings;
    }

    //angry
    public String getangrysongtitle() {
        return angrysongtitle;
    }

    public String getangrysongratings(){
        return angrysongratings;
    }
    public String getangrybooktitle() {
        return angrybooktitle;
    }

    public String getangrybookratings(){
        return angrybookratings;
    }

    public String getangrywheretitle() {
        return angrywheretitle;
    }

    public String getangrywhereratings(){
        return angrywhereratings;
    }

}
