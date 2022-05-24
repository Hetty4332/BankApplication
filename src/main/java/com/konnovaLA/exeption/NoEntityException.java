package com.konnovaLA.exeption;

import lombok.Data;

@Data
public class NoEntityException extends RuntimeException{
    private final String entityName;

}
