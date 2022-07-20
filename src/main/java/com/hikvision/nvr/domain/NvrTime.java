package com.hikvision.nvr.domain;


import lombok.Data;


@Data
public class NvrTime {

    private Integer dwYear;


    private Integer dwMonth;


    private Integer dwDay;


    private Integer dwHour;

    private Integer dwMinute;


    private Integer dwSecond;
}
