package me.antoniocaccamo.poc.mongodb.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author antonio  on 02/10/2020
 */

@Data
public class Tour {

    private String tourPackage;
    private String tourBlurb;
    private String tourName;
    private Integer tourLength;
    private String tourTags;
    private String tourDescription;
    private String tourRegion;
    private BigDecimal tourPrice;
    private String tourDifficulty;
    private String tourBullets;

}
