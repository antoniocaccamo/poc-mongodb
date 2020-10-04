package me.antoniocaccamo.poc.mongodb.model;

import lombok.Data;

import java.util.ArrayList;

import org.bson.types.ObjectId;


/**
 * @author antonio  on 02/10/2020
 */

@Data
public class Tour {

    private ObjectId id;
    private String tourPackage;
    private String tourBlurb;
    private String tourName;
    private Integer tourLength;
    private ArrayList<String> tourTags;
    private String tourDescription;
    private String tourRegion;
    private Integer tourPrice;
    private String tourDifficulty;
    private String tourBullets;

}
