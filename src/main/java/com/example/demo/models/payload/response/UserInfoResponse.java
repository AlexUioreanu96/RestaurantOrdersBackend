package com.example.demo.models.payload.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserInfoResponse {

	private String username;
	private String jwt;

}
