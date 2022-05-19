package com.konnovaLA.request;



import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserRequest {

    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String status;
    @NotBlank
    private String nameRole;

}
