package com.example.demo.models.payload.request;



import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
