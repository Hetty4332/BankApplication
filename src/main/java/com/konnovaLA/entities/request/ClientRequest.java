package com.konnovaLA.entities.request;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

    @Data
    @Builder
    public class ClientRequest {
        private Long id;
        @NotBlank
        @Size(max=50)
        private String name;
        private String phoneNumber;//TODO сделать валидацию если пустой то написать не указан
        @Email
        private String email;
        @Size(max=10,min = 10)
        private String passportNumber;

}
