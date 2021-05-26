package com.bank.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
public boolean validateUser(String username, String password) {

return username.equalsIgnoreCase("Chayy")
&& password.equalsIgnoreCase("chayy");
}
}


