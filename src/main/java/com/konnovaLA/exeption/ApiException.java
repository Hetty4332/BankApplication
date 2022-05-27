package com.konnovaLA.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends Exception{
    private String messsage;
}
