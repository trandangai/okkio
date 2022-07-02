package io.okkio.services;

public interface EmailServices {
    boolean sendMailForgetPassword(String email, String firstName, String password);
}