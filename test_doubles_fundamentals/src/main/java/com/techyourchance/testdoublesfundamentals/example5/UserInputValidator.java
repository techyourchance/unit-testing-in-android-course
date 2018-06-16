package com.techyourchance.testdoublesfundamentals.example5;

public class UserInputValidator {

    public boolean isValidFullName(String fullName) {
        return FullNameValidator.isValidFullName(fullName);
    }

    public boolean isValidUsername(String username) {
        return ServerUsernameValidator.isValidUsername(username);
    }
}
