package co.oudom.ecommerce.features.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResetPasswordRequest(

        @NotBlank(message = "Email is request")
        String email,

        @NotBlank(message = "Reset Code is request")
        String resetCode,

        @NotBlank(message = "Confirmed Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
                message = "Password must contain minimum 8 characters in length, " +
                        "at least one uppercase English letter, " +
                        "at least one lowercase English letter, " +
                        "at least one digit," +
                        "at least one special character.")
        String newPassword,

        @NotBlank(message = "Confirmed Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
                message = "Password must contain minimum 8 characters in length, " +
                        "at least one uppercase English letter, " +
                        "at least one lowercase English letter, " +
                        "at least one digit," +
                        "at least one special character.")
        String confirmPassword

) {
}
