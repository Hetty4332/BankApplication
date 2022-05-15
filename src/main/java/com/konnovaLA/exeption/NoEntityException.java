package com.konnovaLA.exeption;

import lombok.Data;

@Data
public class NoEntityException extends Exception{
    private final String entityName;


}
