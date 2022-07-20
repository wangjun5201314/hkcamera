package com.hikvision.nvr.domain;


import lombok.Data;

import java.util.Date;


@Data
public class PlayBack {

    private Date startTime;


    private Date endTime;


    private Integer channelNumber ;


    private int urlType;
}
