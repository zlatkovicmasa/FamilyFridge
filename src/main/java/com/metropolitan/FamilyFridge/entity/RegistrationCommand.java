package com.metropolitan.FamilyFridge.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class RegistrationCommand {
    @NotBlank(message = "Email ne sme da bude prazan!")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email mora da bude validna adresa!"
    )
    private String username;

    @NotBlank(message = "Lozinka ne sme da bude prazna!")
    @Size(min = 8, message = "Lozinka mora da ima bar 8 karaktera!")
    private String password;

    private String repeatPassword;

    public RegistrationCommand() {
    }

    public RegistrationCommand(String username, String password, String repeatPassword) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public RegistrationCommand setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationCommand setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public RegistrationCommand setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationCommand command = (RegistrationCommand) o;
        return Objects.equals(username, command.username) && Objects.equals(password, command.password) && Objects.equals(repeatPassword, command.repeatPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, repeatPassword);
    }
}
